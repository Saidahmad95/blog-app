package com.example.blogapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtReponseDto {
    private Long id;
    private String username;
    // private String type = "Bearer ";
    private String token;
    private List<String> roles;

}
