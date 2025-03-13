//package com.amazon.ecommerce.securityconfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityFilterConfig {
//
//	@Autowired
//	private CustomUserDetailsService userDetailsService;
//
//	@Autowired
//	private JwtRequestAuth jwtRequestAuth;
//
//	@Bean
//	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//		return configuration.getAuthenticationManager();
//	}
//
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf(customizer -> customizer.disable())
//				.authorizeHttpRequests(requests -> requests.requestMatchers("/signup/register", "/login/log")
//						.permitAll() // routes
//						.requestMatchers("/admin/getAllCategories").hasAnyRole("ADMIN", "BUYER", "SELLER")
//						.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/seller/**").hasRole("SELLER")
//						.requestMatchers("/buyer/**").hasRole("BUYER").anyRequest().authenticated())
//				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.addFilterBefore(jwtRequestAuth, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}
//
//	@Bean
//	DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService);
//		System.out.println("Password Authentication");
//		authProvider.setPasswordEncoder(passwordEncoder()); // Assuming password encoding is used
//		return authProvider;
//	}
//
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}