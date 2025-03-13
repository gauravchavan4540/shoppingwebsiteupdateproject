//package com.amazon.ecommerce.securityconfig;
//
//import java.util.Collection;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.amazon.ecommerce.Model.User;
//import com.amazon.ecommerce.Repo.UserRepo;
//
//import java.util.Collections;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//	@Autowired
//	private UserRepo userRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepository.findByUserName(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found");
//		}
//		System.out.println("User Found");
//		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUserName(),
//				user.getPassword(), getAuthorities(user));
//		System.out.println(details.toString());
//		return details;
//	}
//
//	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
//		String roleName = getRoleNameFromAccountType(user.getAccountType());
//		System.out.println("Role Name is:"+roleName);
//		return Collections.singletonList(new SimpleGrantedAuthority(roleName));
//	}
//
//	private String getRoleNameFromAccountType(int accountType) {
//		System.out.println("Account Type:" + accountType);
//		switch (accountType) {
//		case 1:
//			return "ROLE_ADMIN";
//		case 2:
//			return "ROLE_SELLER";
//		case 3:
//			return "ROLE_BUYER";
//		default:
//			throw new IllegalArgumentException("Invalid account type: " + accountType);
//		}
//	}
//}