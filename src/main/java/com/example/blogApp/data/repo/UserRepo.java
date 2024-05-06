package com.example.blogApp.data.repo;

import com.example.blogApp.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
