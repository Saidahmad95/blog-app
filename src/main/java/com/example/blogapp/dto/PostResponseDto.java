package com.example.blogapp.dto;

import com.example.blogapp.entities.Post;

import lombok.Data;

@Data
public class PostResponseDto {

	private Long id;
	private Long userId;
	private String username;
	private String title;
	private String text;

	public PostResponseDto(Post postEntity) {
		this.id = postEntity.getId();
		this.userId = postEntity.getUser().getId();
		this.username = postEntity.getUser().getUsername();
		this.title = postEntity.getTitle();
		this.text = postEntity.getText();
	}
}
