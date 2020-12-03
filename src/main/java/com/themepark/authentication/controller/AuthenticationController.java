package com.themepark.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.themepark.authentication.exception.InvalidTokenException;
import com.themepark.authentication.model.AuthenticationResponse;
import com.themepark.authentication.model.User;
import com.themepark.authentication.service.MyUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthenticationController {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@PostMapping("/register-user")
	public ResponseEntity<?> addUserDetails(@RequestBody User user) {
		
		log.info("START-Controller for register user.");
		myUserDetailsService.addUserDetails(user);
		String message = "User registered successfully.";
		log.info("END-Controller for register user.");
		return new ResponseEntity<>(message, HttpStatus.OK);
		
	
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
		
		log.info("START-Controller for authenticate user.");
		return myUserDetailsService.generateToken(user);
	
	}
	
	@GetMapping("/validate-token")
	public ResponseEntity<AuthenticationResponse> validateJwtToken(@RequestHeader(name = "Authorization") String jwtTokenFromUser) throws InvalidTokenException {
		
		log.info("START-Controller for validate token given by the user.");
		return myUserDetailsService.validateToken(jwtTokenFromUser);
		
	}
	
}
