package com.bugtracker;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.exceptions.UnAuthorizedLogin;
import com.bugtracker.security.AuthRequest;
import com.bugtracker.security.UserService;
import com.bugtracker.security.util.JwtUtil;


@RestController
//@RequestMapping("")
public class HomeController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authManger;
	
	@GetMapping("/")
	public String home() {
		return "Welcome to Bug Tracker Api";
	}
	
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authReq) throws Exception {
		Authentication auth;
		try {
			
			auth = authManger.authenticate(
					new UsernamePasswordAuthenticationToken(
							authReq.getUserName(),
							authReq.getPassword())
					);
			
		}catch(Exception e) {
			
			throw new UnAuthorizedLogin("Invalid username or password !!");
		}
		return jwtUtil.generateToken(auth);
	}
	
	
	


}
