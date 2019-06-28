package com.mitrais.javabootcamp.charles.springbootxmongodb.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mitrais.javabootcamp.charles.springbootxmongodb.entity.User;

public interface UserService extends UserDetailsService {
	
	Optional<User> findByEmail(String email);
	
	void save(User user);

}
