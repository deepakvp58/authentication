package com.themepark.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.themepark.authentication.model.ExceptionObject;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<Object> invalidTokenExceptionHandler(InvalidTokenException invalidTokenException) {
		return new ResponseEntity<>(new ExceptionObject(HttpStatus.UNAUTHORIZED, invalidTokenException.getMessage()), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> usernameNotFoundExceptionHandler(UsernameNotFoundException usernameNotFoundException) {
		return new ResponseEntity<>(new ExceptionObject(HttpStatus.NOT_FOUND, usernameNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
	}

}
