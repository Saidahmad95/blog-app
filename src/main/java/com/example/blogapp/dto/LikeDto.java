package com.example.blogapp.dto;

import com.example.blogapp.entities.Like;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeDto {
	Long id;
	Long userId;
	Long postId;

	public LikeDto(Like like) {
		this.id = like.getId();
		this.userId = like.getUser().getId();
		this.postId = like.getPost().getId();
	}

}
