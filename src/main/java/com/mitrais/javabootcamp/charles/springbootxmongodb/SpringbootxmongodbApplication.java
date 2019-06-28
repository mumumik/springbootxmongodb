package com.mitrais.javabootcamp.charles.springbootxmongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.mitrais.javabootcamp.charles.springbootxmongodb.entity.Role;
import com.mitrais.javabootcamp.charles.springbootxmongodb.repository.RoleRepository;

@SpringBootApplication
public class SpringbootxmongodbApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootxmongodbApplication.class, args);
	}
	
	/**
     * Used when run as WAR
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringbootxmongodbApplication.class);
    }
	
	@Bean
	CommandLineRunner init(RoleRepository roleRepository) {
		return args ->{
			
			Role adminRole = roleRepository.findByRole("ADMINISTRATOR");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMINISTRATOR");
				roleRepository.save(newAdminRole);
			}
			
			Role userRole = roleRepository.findByRole("USER");
			if (userRole == null) {
				Role newUserRole = new Role();
				newUserRole.setRole("USER");
				roleRepository.save(newUserRole);
			}
		};
	}

}
