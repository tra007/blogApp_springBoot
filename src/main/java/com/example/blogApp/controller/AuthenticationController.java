package com.example.blogApp.controller;

import com.example.blogApp.Expetions.DuplicateUsernameException;
import com.example.blogApp.requestFormat.AuthenticateRequest;
import com.example.blogApp.responseFormat.AuthenticationResponse;
import com.example.blogApp.requestFormat.RegisterRequest;
import com.example.blogApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService authService) {
        this.userService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed. Please check your input.");
        }
        try {
            return ResponseEntity.ok(userService.save(registerRequest));
        } catch (DuplicateUsernameException ex) {
            return ResponseEntity.badRequest().body("duplicated username");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticateRequest request
    ) {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}
