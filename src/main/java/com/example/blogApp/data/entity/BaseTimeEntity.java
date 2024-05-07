package com.example.blogApp.data.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseTimeEntity extends BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}

