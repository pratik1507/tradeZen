package com.tradeZen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradeZen.config.JwtProvider;
import com.tradeZen.model.User;
import com.tradeZen.repository.UserRepository;
import com.tradeZen.response.AuthResponse;

import io.jsonwebtoken.JwtParser;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user)throws Exception{
				
		User isEmailExit = userRepository.findByEmail(user.getEmail());
		
		if(isEmailExit!=null) {
			throw new Exception("email is alreay used with another account");
		}
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFullName(user.getFullName());
		newUser.setPassword(user.getPassword());
		
		User savedUser = userRepository.save(newUser);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(
				user.getEmail(), 
				user.getPassword()
		);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt = JwtProvider.generateToken(auth);
		
		AuthResponse res=new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("registration success");
				
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
}
