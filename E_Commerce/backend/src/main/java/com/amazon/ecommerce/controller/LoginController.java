package com.amazon.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.ecommerce.Model.LoginReturn;
import com.amazon.ecommerce.Model.User;
import com.amazon.ecommerce.Repo.UserRepo;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("login")
public class LoginController {

	@Autowired
	private UserRepo userRepo;

//	@Autowired
//	private UserService service;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@GetMapping("test")
	public String sample() {
		return "Welcome";
	}

	@RequestMapping("getName{id}")
	public String[] getName(@PathVariable int id) {
		User user = userRepo.findById(id).orElse(null);
		String[] sa = new String[1];
		if (user != null) {
			sa[0] = user.getName();
		} else {
			sa[0] = "User not found";
		}
		return sa;
	}

	@RequestMapping("log")
	public LoginReturn login(@RequestBody String[] sa) {
		if (sa == null || sa.length < 2)
			return new LoginReturn(-1, -1, "Corrupted Data");

		String username = sa[0];
		String password = sa[1];

		if (username == null || username.isEmpty())
			return new LoginReturn(-1, -1, "Enter Username");

		if (password == null || password.isEmpty())
			return new LoginReturn(-1, -1, "Enter Password");

		int count = userRepo.countByUserName(username);

		if (count == 0)
			return new LoginReturn(-1, -1, "Wrong Username");

		if (count > 1)
			return new LoginReturn(-1, -1, "Multiple users with the same username found");

		User user = userRepo.findByUserName(username);

//		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
		if (user != null && password.equals(user.getPassword())) {
			return new LoginReturn(user.getId(), user.getAccountType(), null);
//			return service.verifyUser(sa);
		} else {
			return new LoginReturn(-1, -1, "Wrong Password");
		}
	}
}