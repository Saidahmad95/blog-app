package com.example.blogapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.dto.CommentDto;
import com.example.blogapp.entities.Comment;
import com.example.blogapp.service.CommentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/comments")
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/all")
	public ResponseEntity<List<Comment>> getAllComments(@RequestParam Optional<Long> userId,
			@RequestParam Optional<Long> postId) {
		return commentService.getAllComments(userId, postId);
	}

	@PostMapping
	public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto) {
		return commentService.createComment(commentDto);
	}

	@GetMapping("/{commentId}")
	public ResponseEntity<Comment> getCommentByCommentId(@PathVariable Long commentId) {
		return commentService.getCommentByCommentId(commentId);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<Comment> updateCommentByCommentId(@PathVariable Long commentId,
			@RequestBody CommentDto commentDto) {
		return commentService.updateCommentByCommentId(commentId,commentDto);
	}
	
	@DeleteMapping("/{commentId}")
	public void deleteCommentByCommentId(@PathVariable Long commentId) {
		commentService.deleteCommentByCommentId(commentId);
	}

}
