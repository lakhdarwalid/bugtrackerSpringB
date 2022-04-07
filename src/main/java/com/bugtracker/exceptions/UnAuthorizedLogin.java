package com.bugtracker.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class UnAuthorizedLogin extends BadCredentialsException{

	
	private static final long serialVersionUID = 1L;

	public UnAuthorizedLogin(String msg) {
		super(msg);
		
	}

}
