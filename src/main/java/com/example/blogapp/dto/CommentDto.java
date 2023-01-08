package com.example.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {

	private Long postId;
	private Long userId;
	private String text;

	// For update comment
	public CommentDto(String text1) {
		this.text = text1;
	}

}
