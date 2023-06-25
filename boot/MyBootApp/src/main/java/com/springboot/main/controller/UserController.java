package com.springboot.main.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/login") // by the time we reach here, spring has already verified username/password
	public UserDetails login(Principal principal) {
		String username = principal.getName();
		UserDetails user = userService.loadUserByUsername(username);
		return user;
	}
	
}