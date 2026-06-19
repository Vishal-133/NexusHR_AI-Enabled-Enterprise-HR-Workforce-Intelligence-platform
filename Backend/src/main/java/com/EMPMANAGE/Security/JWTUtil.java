package com.EMPMANAGE.Security;

import java.security.Key;
import java.util.*;

import org.springframework.stereotype.Component;

import com.EMPMANAGE.Entity.UserAuthentication;
import com.EMPMANAGE.Enum.Permission;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	private final Key key;
	private final long expireTime = 1000L * 60 * 60 * 12;
	
	public JWTUtil() {
		
		String secret = System.getenv("JWT_SECRET");
		if (secret == null || secret.isEmpty()) {
			// FIXED: Made the fallback string longer than 32 characters to prevent WeakKeyException
			secret = "Replace_this_with_a_secret_Key_that_is_long_enough_12345";
		}
		
		key = Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public String generateToken(UserAuthentication user) {
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRole().name());
		
		Set<Permission> Prems = RoleBasedPermission.getRoleBasedPermission().get(user.getRole());
		
		Date now = new Date();
		Date expires = new Date(now.getTime() + expireTime);
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getUserOfficialEmail())
				.setIssuedAt(now)
				.setExpiration(expires)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	public boolean validToken(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
			
		} catch (JwtException e) {
			return false;
		}
	}
	
	public Claims getClaim(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
	public String getUserEmail(String token) {
		return getClaim(token).getSubject();
	}
}