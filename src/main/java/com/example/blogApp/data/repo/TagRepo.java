package com.example.blogApp.data.repo;

import com.example.blogApp.data.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepo extends JpaRepository<Tag, Integer> {
}
