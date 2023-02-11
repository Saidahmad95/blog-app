package com.example.blogapp.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.blogapp.dto.LikeDto;
import com.example.blogapp.dto.PostDto;
import com.example.blogapp.dto.PostResponseDto;
import com.example.blogapp.entities.Like;
import com.example.blogapp.entities.Post;
import com.example.blogapp.entities.User;
import com.example.blogapp.repositories.LikeRepository;
import com.example.blogapp.repositories.PostRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;
	private final LikeService likeService;

	public ResponseEntity<List<PostResponseDto>> getAllPosts(Optional<Long> userId) {
		List<Post> list;
		if (userId.isPresent()) {
			list = postRepository.findByUserId(userId.get());
		} else {
			list = postRepository.findAll();
		}
		List<PostResponseDto> mappedList = list.stream().map(post -> {
ResponseEntity<List<LikeDto>> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null),
					Optional.of(post.getId()));
			return new PostResponseDto(post, likes.getBody());
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(mappedList);
	}

	public ResponseEntity<Post> getPostByPostId(Long postId) {
		Post post = postRepository.findById(postId).orElse(null);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

	public ResponseEntity<Post> createPost(@Valid PostDto newPost) throws UserPrincipalNotFoundException {
		User foundUser = userService.getUserByUserId(newPost.getUserId()).getBody();
		Post post1 = new Post();
		post1.setUser(foundUser);
		post1.setTitle(newPost.getTitle());
		post1.setText(newPost.getText());
		Post savedPost = postRepository.save(post1);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
	}

	public ResponseEntity<Post> updatePostByPostId(Long postId, PostDto postDto) {
		Post foundPost = getPostByPostId(postId).getBody();
		if (foundPost != null) {
			if (postDto.getText() != null) {
				foundPost.setText(postDto.getText());
			}
			if (postDto.getTitle() != null) {
				foundPost.setTitle(postDto.getTitle());
			}

			Post updatedPost = postRepository.save(foundPost);
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedPost);
		}
		return null;
	}

	public void deletePostByPostId(Long postId) {
		postRepository.deleteById(postId);

	}

}
