package com.amazon.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.ecommerce.Model.ApiResponse;
import com.amazon.ecommerce.Model.User;
import com.amazon.ecommerce.Repo.UserRepo;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("signup")
public class SignupController {

	@Autowired
	private UserRepo userRepo;

//    @Autowired
//    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

	@PostMapping("register")
	public ApiResponse registerUser(@RequestBody User user) {
		int isUser = userRepo.countByUserName(user.getUserName());
		if (isUser >= 1) {
			return new ApiResponse(0, "Username already exists");
		} else {
//        	user.setPassword(encoder.encode(user.getPassword()));
			user.setPassword(user.getPassword());
			userRepo.save(user);
			return new ApiResponse(1, "User registered successfully");
		}
	}
}