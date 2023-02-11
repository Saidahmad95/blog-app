package com.example.blogapp.dto;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.blogapp.entities.Like;
import com.example.blogapp.entities.Post;

import lombok.Data;

@Data
public class PostResponseDto {
	private Long id;
	private Long userId;
	private String username;
	private String title;
	private String text;
	List<LikeDto> postLikes;

	public PostResponseDto(Post postEntity,List<LikeDto> likes) {
		this.id = postEntity.getId();
		this.userId = postEntity.getUser().getId();
		this.username = postEntity.getUser().getUsername();
		this.title = postEntity.getTitle();
		this.text = postEntity.getText();	
		this.postLikes=likes;
	}
}
