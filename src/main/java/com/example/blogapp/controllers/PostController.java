package com.example.blogapp.controllers;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.dto.PostDto;
import com.example.blogapp.entities.Post;
import com.example.blogapp.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/all")
	public ResponseEntity<List<Post>> getAllPosts(@RequestParam Optional<Long> userId) {
		return postService.getAllPosts(userId);
	}
	
	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody PostDto newPost) throws UserPrincipalNotFoundException{
		return  postService.createPost(newPost);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<Post> getPostByPostId(@PathVariable Long postId)  {
		return postService.getPostByPostId(postId);
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<Post> updatePostByPostId(@PathVariable Long postId,@RequestBody PostDto postDto){
		return postService.updatePostByPostId(postId,postDto);
	}
	
	@DeleteMapping("/{postId}")
	public void deletePostByPostId(@PathVariable Long postId) {
		postService.deletePostByPostId(postId);
	}
	
	
	
	
}
