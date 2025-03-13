//package com.amazon.ecommerce.securityconfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class JwtRequestAuth extends OncePerRequestFilter {
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Autowired
//	private JWTService jwtService;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException {
//
//		final String authorizationHeader = request.getHeader("Authorization");
//		System.out.println("In auth header:" + authorizationHeader);
//		String username = null;
//		String jwt = null;
//
//		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//			jwt = authorizationHeader.substring(7);
//			username = jwtService.extractUsername(jwt);
//			System.out.println("1name is:" + username + "  generated token is :" + jwt);
//		}
//
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//			System.out.println("1" + userDetails.toString());
//			if (jwtService.validateToken(jwt, userDetails)) {
//				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				System.out.println("1 " + authenticationToken);
//				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				System.out.println("2 " + authenticationToken);
//				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//			}
//		}
//		System.out.println("2name is:" + username + "  generated token is :" + jwt);
//		chain.doFilter(request, response);
//	}
//}