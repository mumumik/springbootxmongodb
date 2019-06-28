package com.mitrais.javabootcamp.charles.springbootxmongodb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mitrais.javabootcamp.charles.springbootxmongodb.entity.Role;
import com.mitrais.javabootcamp.charles.springbootxmongodb.entity.User;
import com.mitrais.javabootcamp.charles.springbootxmongodb.repository.RoleRepository;
import com.mitrais.javabootcamp.charles.springbootxmongodb.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> result = userRepository.findByEmail(email);
		User user = null;
		if(result.isPresent()) {
			user = result.get();
		}
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach((role)->{
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		});
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		return grantedAuthorities;
	}
	
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		Role userRole = roleRepository.findByRole("ADMINISTRATOR");
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

}
