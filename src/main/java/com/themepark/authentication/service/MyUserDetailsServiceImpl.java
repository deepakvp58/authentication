package com.themepark.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.themepark.authentication.exception.InvalidTokenException;
import com.themepark.authentication.model.AuthenticationResponse;
import com.themepark.authentication.model.MyUserDetails;
import com.themepark.authentication.model.User;
import com.themepark.authentication.repository.UserRepository;
import com.themepark.authentication.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<?> generateToken(User user) {

		log.info("START-Service to generate jwt token.");
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
			final UserDetails userDetails = loadUserByUsername(user.getUserName());
			final String jwtToken = jwtUtil.generateToken(userDetails);
			log.info("END-Service to generate jwt token.");
			return new ResponseEntity<>(new AuthenticationResponse(userDetails.getUsername(), true, jwtToken),
					HttpStatus.OK);
		} catch (Exception ex) {
			log.error("User name not found.");
			throw new UsernameNotFoundException("Incorrect username or password.");
		}

	}

	@Override
	public ResponseEntity<AuthenticationResponse> validateToken(String jwtTokenFromUser) throws InvalidTokenException {

		log.info("START-Service to validate jwt token.");
		String jwtToken = jwtTokenFromUser.substring(7);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		if (jwtUtil.validateToken(jwtToken)) {
			authenticationResponse.setUserName(jwtUtil.extractUsername(jwtToken));
			authenticationResponse.setValid(true);
		} else {
			authenticationResponse.setValid(false);
		}
		
		log.info("END-Service to validate jwt token.");
		return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {

		log.info("START-Service to load the user object by the name.");
		Optional<User> user = userRepository.findByUserName(userName);
		log.info("END-Service to load the user object by the name.");
		return user.map(MyUserDetails::new).get();

	}

	@Override
	public void addUserDetails(User user) {

		log.info("START-Service to save the user object by the name.");
		userRepository.save(user);
		log.info("END-Service to save the user object by the name.");

	}

}
