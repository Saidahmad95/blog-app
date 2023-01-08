package com.example.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long>{

}	
