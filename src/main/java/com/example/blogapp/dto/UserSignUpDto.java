package com.example.blogapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpDto {
    private String username;
    private String password;
    private List<String> roles;
}
