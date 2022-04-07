package com.bugtracker.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	
	@GetMapping("/users")
	@JsonView(View.User.class)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping("/users")
	@JsonView(View.User.class)
	public  ResponseEntity<User> addUser(@RequestBody User user){
			return new ResponseEntity<User>(userService.addUser(user), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/users/{id}")
	@JsonView(View.User.class)
	public User findById(@PathVariable long id) {
			return userService.getUserById(id);
	}
	
	@PutMapping("/users/{id}")
	@JsonView(View.User.class)
	public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user){
		return new ResponseEntity<User>(userService.updateUserById(id, user), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/users/{id}")
	@JsonView(View.User.class)
	public ResponseEntity<Integer> deleteUser(@PathVariable long id) {
		userService.deleteUserById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/users/user={name}")
	@JsonView(View.User.class)
	public ResponseEntity<User> getUserByName(@PathVariable String name) {
		return new ResponseEntity<User>(userService.getUserByUserName(name), HttpStatus.FOUND);
	}

	@GetMapping("/users/userlike={name}")
	@JsonView(View.User.class)
	public List<User> getUsersLikeName(@PathVariable String name){
		return userService.getUsersLikeUserName(name);
	}
	

}
