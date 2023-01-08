package com.example.blogapp.controllers;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.entities.User;
import com.example.blogapp.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User newUser) {
		return userService.createUser(newUser);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable Long userId)  {
		return userService.getUserByUserId(userId);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUserByUserId(@PathVariable Long userId, @RequestBody User user) throws  UserPrincipalNotFoundException {
		return userService.updateUserByUserId(userId, user);
	}

	@DeleteMapping("/{userId}")
	public void deleteUserByUserId(@PathVariable Long userId) throws NotFoundException, UserPrincipalNotFoundException {
		userService.deleteUserByUserId(userId);
	}

}
