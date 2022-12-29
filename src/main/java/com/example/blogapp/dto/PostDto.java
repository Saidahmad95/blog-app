package com.example.blogapp.dto;


import lombok.Data;

@Data
public class PostDto {
	private Long userId;
	
	private String title;

	private String text;
	
	public PostDto(String title1, String text1) {
		title=title1;
		text=text1;
	}

}
