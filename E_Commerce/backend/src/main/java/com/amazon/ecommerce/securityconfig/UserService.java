//package com.amazon.ecommerce.securityconfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import com.amazon.ecommerce.Model.LoginReturn;
//import com.amazon.ecommerce.Model.User;
//import com.amazon.ecommerce.Repo.UserRepo;
//
//@Service
//public class UserService {
//
//	@Autowired
//	UserRepo repo;
//
//	@Autowired
//	AuthenticationManager authenticationManager;
//
//	@Autowired
//	JWTService jwtService;
//
//	public LoginReturn verifyUser(String[] userData) {
//		Authentication authentication = authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(userData[0], userData[1]));
//		
//		System.out.println("Authentication Status:" + authentication.isAuthenticated());
//		
//		if (authentication.isAuthenticated()) {
//			User user = repo.findByUserName(userData[0]);
//			String token = jwtService.generateToken(userData[0]);
//			System.out.println("Token is:" + token);
//			// Assuming you're storing user id and user type
//			return new LoginReturn(user.getId(), user.getAccountType(), token);
//		} else {
//			return new LoginReturn(-1, -1, "Authentication failed");
//		}
//	}
//}
