package com.bugtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.security.User;
import com.bugtracker.security.UserService;
import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class PasswordRecoveryController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/passwordrecovery/exist={username}")
	public boolean doesUserExist(@PathVariable String username){
		return userService.doesUserExist(username);
	}
	
	@GetMapping("/passwordrecovery/match={username}/{qst1}/{qst2}/{ans1}/{ans2}")
	@JsonView(View.User.class)
	public User matchUser(@PathVariable String username, @PathVariable String qst1, @PathVariable String qst2, @PathVariable String ans1, @PathVariable String ans2) {
		return userService.matchUser(username, qst1, qst2, ans1, ans2);
	}
	
	@PutMapping("/passwordrecovery/upd={id}/{password}")
	public User updatePassword(@PathVariable long id, @PathVariable String password) {
		return userService.updatePassword(id, password);
	}
}
