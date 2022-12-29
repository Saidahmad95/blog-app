package com.example.blogapp.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.blogapp.dto.PostDto;
import com.example.blogapp.entities.Post;
import com.example.blogapp.entities.User;
import com.example.blogapp.repositories.PostRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PostService {
	
	private final PostRepository postRepository;
	private final UserService userService;

	public ResponseEntity<List<Post>> getAllPosts(Optional<Long> userId) {
		if(userId.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(postRepository.findByUserId(userId.get())); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(postRepository.findAll());
	}

	public ResponseEntity<Post> getPostByPostId(Long postId)   {
	Post post = postRepository.findById(postId).orElse(
			null);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

	public ResponseEntity<Post> createPost(PostDto newPost) throws UserPrincipalNotFoundException {
		User foundUser = userService.getUserByUserId(newPost.getUserId()).getBody();
		Post post1=new Post();
		post1.setUser(foundUser);
		post1.setTitle(newPost.getTitle());
		post1.setText(newPost.getText());
		Post savedPost = postRepository.save(post1);
	
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
	}

	public ResponseEntity<Post> updatePostByPostId(Long postId, PostDto postDto) {
		Post foundPost = getPostByPostId(postId).getBody();
		if(foundPost!=null) {
			foundPost.setText(postDto.getText());
			foundPost.setTitle(postDto.getTitle());
		}
		return null;
	}

	public void deletePostByPostId(Long postId) {
		postRepository.deleteById(postId);
		
	}

	

		
	
		
	}


	


