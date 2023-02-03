package com.example.blogapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeDto {
    // Long id;
	Long userId;
	Long postId;
}
