package com.mitrais.javabootcamp.charles.springbootxmongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mitrais.javabootcamp.charles.springbootxmongodb.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByEmail(String email);
	
}
