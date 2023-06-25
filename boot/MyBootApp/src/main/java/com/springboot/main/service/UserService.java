package com.springboot.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.ResourceNotFoundException;
import com.springboot.main.model.User;
import com.springboot.main.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		return user;
	}
	
	public User insert(User user) {
		return userRepository.save(user);
	}
	
	public User getById(int id) throws ResourceNotFoundException {
		Optional<User> userFound = userRepository.findById(id);
		
		if (userFound.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return userFound.get();
	}

	public void delete(int id) {
		userRepository.deleteById(id);
	}
	
}