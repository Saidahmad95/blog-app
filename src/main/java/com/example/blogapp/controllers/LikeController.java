package com.example.blogapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.dto.LikeDto;
import com.example.blogapp.entities.Like;
import com.example.blogapp.service.LikeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<List<Like>> getAllLikes(
            @RequestParam Optional<Long> userId,
            @RequestParam Optional<Long> postId) {

        return likeService.getAllLikesWithParam(userId, postId);
    }

    @PostMapping
    public ResponseEntity<Like> createLike(@RequestBody LikeDto likeDto) {
        return likeService.createLike(likeDto);
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<Like> getLikeByLikeId(@PathVariable Long likeId){
        return likeService.getLikeByLikeId(likeId);
    }
    

    @DeleteMapping("/{likeId}")
    public void deleteLikeByLikeId(@PathVariable Long likeId){
     likeService.deleteLikeByLikeId(likeId);
    }

}
