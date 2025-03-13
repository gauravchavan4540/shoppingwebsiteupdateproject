//package com.amazon.ecommerce.securityconfig;
//
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//@Service
//public class JWTService {
//	private String secretKey = "";
//
//	public JWTService() {
//		try {
//			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//			SecretKey sk = keyGenerator.generateKey();
//			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//			System.out.println("Secrete Key is:" + secretKey);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//	}
//
//	String generateToken(String username) {
//		Map<String, Object> claims = new HashMap<>();
//		System.out.println("Generating Token with name :" + username);
//		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 60000*2)) // 1 minute
//				.signWith(getKey()).compact();
//	}
//
//	public String extractUsername(String token) {
//		return extractClaim(token, Claims::getSubject);
//	}
//
//	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = extractAllClaims(token);
//
//		return claimsResolver.apply(claims);
//	}
//
//	private Claims extractAllClaims(String token) {
//		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
//	}
//
//	public Boolean validateToken(String token, UserDetails userDetails) {
//		final String username = extractUsername(token);
//		System.out.println("Validating token with name:" + username);
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
//
//	private Boolean isTokenExpired(String token) {
//		return extractExpiration(token).before(new Date());
//	}
//
//	private Date extractExpiration(String token) {
//		System.out.println("Checking Token Expiration");
//		return extractClaim(token, Claims::getExpiration);
//	}
//
//	private Key getKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}
//}