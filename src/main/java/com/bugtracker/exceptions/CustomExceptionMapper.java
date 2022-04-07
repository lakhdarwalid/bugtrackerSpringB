package com.bugtracker.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionMapper {
	
	@ExceptionHandler(value = {ResourceNotFoundException.class})
	public ResponseEntity<Object> handleException(ResourceNotFoundException ex){
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
		return new ResponseEntity<Object>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
		return new ResponseEntity<Object>(errorMessage, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = {UnAuthorizedLogin.class})
	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex){
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
		return new ResponseEntity<Object>(errorMessage, HttpStatus.UNAUTHORIZED);
	}
	
	
}
