package com.EMPMANAGE.Security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // 👈 1. ADD THIS IMPORT
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity // 👈 2. ADD THIS ANNOTATION HERE TO ACTIVATE @PreAuthorize!
public class SecurityConfig {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JWTFilter jwtFilter; 
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception { 
	    http
	        .cors(cors -> cors.configurationSource(request -> {
	            CorsConfiguration cfg = new CorsConfiguration();
	            cfg.setAllowedOrigins(List.of("*"));
	            cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	            cfg.setAllowedHeaders(List.of("*"));
	            return cfg;
	        }))
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**", "/auth/**", "/error").permitAll()
	            .anyRequest().authenticated()
	        );
	    
	    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	}
}