package com.example.blogapp.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.blogapp.entities.User;
import com.example.blogapp.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
	}

	public ResponseEntity<User> createUser(User newUser) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(newUser));
	}

	public ResponseEntity<User> getUserByUserId(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	public ResponseEntity<User> updateUserByUserId(Long userId, User user) throws UserPrincipalNotFoundException  {
		User foundUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserPrincipalNotFoundException(String.format("String with user id : %d not found!",userId)));
		foundUser.setUsername(user.getUsername());
		foundUser.setPassword(user.getPassword());
		User savedUser = userRepository.save(foundUser);
		return ResponseEntity.status(HttpStatus.OK).body(savedUser);
	}

	public void deleteUserByUserId(Long userId) throws  UserPrincipalNotFoundException {
		User user = getUserByUserId(userId).getBody();
		userRepository.delete(user);
	}

}
