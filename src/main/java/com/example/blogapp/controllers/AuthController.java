package com.example.blogapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.dto.JwtReponseDto;
import com.example.blogapp.dto.UserLoginDto;

import com.example.blogapp.dto.UserSignUpDto;
import com.example.blogapp.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {
    
private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtReponseDto> login(@RequestBody UserLoginDto userLoginDto){
        return authService.login(userLoginDto);
    } 

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpDto signUpDto ){
return authService.signUp(signUpDto);
    }
}
