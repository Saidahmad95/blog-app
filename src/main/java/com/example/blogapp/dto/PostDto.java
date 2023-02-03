package com.example.blogapp.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private Long id;
	
	private Long userId;
	
	@NotBlank(message = "Post title shouldn't be empty !")
	private String title;

	@NotBlank(message = "Post text shouldn't be empty !")
	private String text;
	
	public PostDto(String title1, String text1) {
		title=title1;
		text=text1;
	}

}
