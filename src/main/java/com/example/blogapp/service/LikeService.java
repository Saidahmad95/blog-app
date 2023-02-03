package com.example.blogapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.blogapp.dto.LikeDto;
import com.example.blogapp.entities.Like;
import com.example.blogapp.entities.Post;
import com.example.blogapp.entities.User;
import com.example.blogapp.repositories.LikeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public ResponseEntity<List<Like>> getAllLikesWithParam(
            Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if (userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAandPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        } else {
            list = likeRepository.findAll();
        }
        return ResponseEntity.ok(list);
    }

    public ResponseEntity<Like> createLike(LikeDto likeDto) {

        ResponseEntity<User> userByUserId = userService.getUserByUserId(likeDto.getUserId());
        ResponseEntity<Post> postByPostId = postService.getPostByPostId(likeDto.getPostId());

        if (userByUserId.getBody() != null && postByPostId.getBody() != null) {
            Like newLike = new Like();
            newLike.setPost(postByPostId.getBody());
            newLike.setUser(userByUserId.getBody());
            Like savedLike = likeRepository.save(newLike);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedLike);
        }
        return ResponseEntity.badRequest().body(null);
    }

    public ResponseEntity<Like> getLikeByLikeId(Long likeId) {
        Optional<Like> findById = likeRepository.findById(likeId);
        return findById.isPresent()
                ? ResponseEntity.ok(findById.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    public void deleteLikeByLikeId(Long likeId) {
        likeRepository.deleteById(likeId);
    }

}
