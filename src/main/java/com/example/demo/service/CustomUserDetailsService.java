package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.CustomUserDetails;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

@Controller
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userData = userRepository.findByUsername(username);
		
		if(userData != null) {
			
			return new CustomUserDetails(userData);
		}
		
		return null;
	}

}
