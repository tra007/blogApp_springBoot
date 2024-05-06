package com.example.blogApp.data.repo;

import com.example.blogApp.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
