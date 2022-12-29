package com.example.blogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
 
	List<Post> findByUserId(Long userId);
	
	
}
