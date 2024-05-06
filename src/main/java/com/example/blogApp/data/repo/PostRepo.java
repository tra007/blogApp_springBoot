package com.example.blogApp.data.repo;

import com.example.blogApp.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {
}
