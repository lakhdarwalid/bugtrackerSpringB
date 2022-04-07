package com.bugtracker;



import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bugtracker.security.Role;
import com.bugtracker.security.RoleRepo;
import com.bugtracker.security.User;
import com.bugtracker.security.UserRepo;


@SpringBootApplication
@Configuration
public class BugTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugTrackerApiApplication.class, args);
	}
	
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedHeaders("*").allowedOrigins("http://bugtrackerng.s3-website-us-east-1.amazonaws.com")//http://bugtrackerng.s3-website-us-east-1.amazonaws.com
				.allowedMethods("POST","GET","DELETE","PUT").allowCredentials(true);
			}
			
		};
	}
	
	@Bean  
	InitializingBean sendDatabase(UserRepo userRepo, RoleRepo roleRepo) {
	    return () -> {
	    	User user;
	    	try {
	    		user= userRepo.findByUserName("admin").get();
	    	}catch(Exception e) {
	    	  	Role role = new Role(1,"admin", null);
	    	  	Role savedRole = roleRepo.save(role);
	    	  	user = new User(1,"admin", "admin", true, savedRole, null, null, null, null, "admin@bugtracker.com", "123-456-7890", null, null);
		        userRepo.save(user);
		        } 
	      };
	   }  
}
