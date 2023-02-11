package com.example.blogapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.blogapp.dto.JwtReponseDto;
import com.example.blogapp.dto.UserLoginDto;
import com.example.blogapp.dto.UserSignUpDto;
import com.example.blogapp.repositories.UserRepository;
import com.example.blogapp.security.jwt.JwtTokenProvider;
import com.example.blogapp.security.user.JwtUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public ResponseEntity<JwtReponseDto> login(UserLoginDto request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtTokenProvider.generateToken(authentication);
            JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new JwtReponseDto(
                            userDetails.getId(),
                            userDetails.getUsername(),
                            jwtToken,
                            roles));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public ResponseEntity<?> signUp(UserSignUpDto signUpDto) {
        if (userRepo.existsByUsername(signUpDto.getUsername())) {
return ResponseEntity
.badRequest()
.body(null);//TO-DO: chala
        }

        return null;
    }

}
