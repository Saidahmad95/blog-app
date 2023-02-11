package com.example.blogapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogapp.dto.JwtReponseDto;
import com.example.blogapp.dto.UserLoginDto;
import com.example.blogapp.dto.UserSignUpDto;
import com.example.blogapp.entities.Role;
import com.example.blogapp.entities.User;
import com.example.blogapp.enums.ERole;
import com.example.blogapp.repositories.RoleRepository;
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
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
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
                    .body("User with username: \"" + signUpDto.getUsername() + "\" already exists !");// TO-DO: chala
        }

        Set<Role> roles = new HashSet<>();

        if (signUpDto.getRoles() == null) {
            Role userRole = roleRepo.findByName(ERole.ROLE_USER).orElseThrow();
            roles.add(userRole);
        } else {
            signUpDto.getRoles()
                    .forEach(role -> {
                        switch (role) {
                            case "admin":
                                Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN).orElseThrow();
                                roles.add(adminRole);
                                break;
                            case "user":
                                Role userRole = roleRepo.findByName(ERole.ROLE_USER).orElseThrow();
                                roles.add(userRole);
                                break;
                            default:
                                LOGGER.error("\"" + role + "\" is not exist !");
                        }
                    });
        }

        User user = new User(
                signUpDto.getUsername(),
                encoder.encode(signUpDto.getPassword()),
                roles);

        User savedRole = userRepo.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedRole);
    }

}
