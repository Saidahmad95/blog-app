package com.example.blogapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogapp.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long>{
 
List<Like> findByUserIdAandPostId(Long userId,Long postId) ;

 List<Like>  findByUserId(Long userId);
    
     List<Like>    findByPostId(Long postId);

  // @Query
}	
