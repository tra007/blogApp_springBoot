package com.example.blogApp.service;

import com.example.blogApp.Expetions.DuplicateUsernameException;
import com.example.blogApp.requestFormat.AuthenticateRequest;
import com.example.blogApp.responseFormat.AuthenticationResponse;
import com.example.blogApp.requestFormat.RegisterRequest;
import com.example.blogApp.data.entity.Role;
import com.example.blogApp.data.entity.User;
import com.example.blogApp.data.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse save(RegisterRequest request) {
        if (!existsByUsername(request.getUsername())) {
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.User);
            user = userRepo.save(user);
            String jwt = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwt).build();
        } else {
            throw new DuplicateUsernameException("Username already exists");
        }
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    public Boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User update(String username, User user) {
        Optional<User> optionalUser = findByUsername(username);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepo.save(existingUser);
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    public User delete(String username) {
        Optional<User> optionalUser = findByUsername(username);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            userRepo.delete(existingUser);
            return existingUser;
        } else {
            throw new NoSuchElementException("User not found");
        }
    }
}
