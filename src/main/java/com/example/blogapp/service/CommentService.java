package com.example.blogapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.blogapp.dto.CommentDto;
import com.example.blogapp.entities.Comment;
import com.example.blogapp.entities.Post;
import com.example.blogapp.entities.User;
import com.example.blogapp.repositories.CommentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostService postService;
	private final UserService userService;

	public ResponseEntity<List<Comment>> getAllComments(Optional<Long> userId, Optional<Long> postId) {

		if (userId.isPresent() && postId.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(commentRepository.findByUserIdAndPostId(userId.get(), postId.get()));
		} else if (userId.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(commentRepository.findByUserId(userId.get()));
		} else if (postId.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(commentRepository.findByPostId(postId.get()));
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

	}

	public ResponseEntity<Comment> getCommentByCommentId(Long commentId) {
		Comment foundComment = commentRepository.findById(commentId).orElse(null);
		return ResponseEntity.status(HttpStatus.OK).body(foundComment);
	}

	public ResponseEntity<Comment> createComment(CommentDto commentDto) {
		User foundUser = userService.getUserByUserId(commentDto.getUserId()).getBody();
		Post foundPost = postService.getPostByPostId(commentDto.getPostId()).getBody();

		if (foundUser != null && foundPost != null) {
			Comment comment = new Comment();
			comment.setPost(foundPost);
			comment.setUser(foundUser);
			comment.setText(commentDto.getText());
			Comment savedComment = commentRepository.save(comment);

			return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	public ResponseEntity<Comment> updateCommentByCommentId(Long commentId, CommentDto commentDto) {
		Comment foundComment = getCommentByCommentId(commentId).getBody();
		if (foundComment != null) {
			foundComment.setText(commentDto.getText());
			Comment savedComment = commentRepository.save(foundComment);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedComment);
		}
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
	}

	public void deleteCommentByCommentId(Long commentId) {
		commentRepository.deleteById(commentId);

	}

}
