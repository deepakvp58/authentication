package com.themepark.authentication.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.themepark.authentication.exception.InvalidTokenException;
import com.themepark.authentication.model.AuthenticationResponse;
import com.themepark.authentication.model.User;

public interface MyUserDetailsService extends UserDetailsService {

	void addUserDetails(User user);

	ResponseEntity<?> generateToken(User user);

	ResponseEntity<AuthenticationResponse> validateToken(String jwtTokenFromUser) throws InvalidTokenException;
}
