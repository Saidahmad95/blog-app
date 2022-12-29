package com.example.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
