package com.mitrais.javabootcamp.charles.springbootxmongodb.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mitrais.javabootcamp.charles.springbootxmongodb.entity.User;
import com.mitrais.javabootcamp.charles.springbootxmongodb.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/login")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@GetMapping(value="/signup")
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("signup");
		return modelAndView;
	}
	
	@PostMapping(value="/signup")
	public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Optional<User> userExisted = userService.findByEmail(user.getEmail());
		if(userExisted.isPresent()) {
			bindingResult.rejectValue("email", "error.user", "Email already registered, please use another email");
		}
		if(bindingResult.hasErrors()) {
			modelAndView.setViewName("signup");
		}else {
			userService.save(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}
	
	@GetMapping(value= "/dashboard")
	public ModelAndView dashboard() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> result = userService.findByEmail(auth.getName());
		User user = result.get();
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome" + user.getFullname());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}
	
	@GetMapping(value = {"/","/home"})
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		return modelAndView;
	}
	
}
