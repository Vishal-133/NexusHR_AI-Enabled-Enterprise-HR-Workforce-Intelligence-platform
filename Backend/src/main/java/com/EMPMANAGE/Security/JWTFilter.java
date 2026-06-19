package com.EMPMANAGE.Security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	// FIX: Explicitly ignore login/register paths so they never drop authentication context
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		return path.startsWith("/api/auth/") || path.startsWith("/auth/");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		String token = null;
		
		if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			token = header.substring(7);
		}
		
		if (token != null && jwtUtil.validToken(token)) {
			String userEmail = jwtUtil.getUserEmail(token);
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			
			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		filterChain.doFilter(request, response);
	}
}