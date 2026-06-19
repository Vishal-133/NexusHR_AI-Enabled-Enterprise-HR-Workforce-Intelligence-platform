package com.EMPMANAGE.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.EMPMANAGE.DTO.LoginRequestDTO;
import com.EMPMANAGE.DTO.AuthResponseDTO;
import com.EMPMANAGE.Entity.UserAuthentication;
import com.EMPMANAGE.Repository.userAuthRepository;
import com.EMPMANAGE.Security.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private userAuthRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserAuthentication user) {
        // Check if user already exists
        if (userRepo.findByUserOfficialEmail(user.getUserOfficialEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        return ResponseEntity.status(201).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDto) {
        // 1. Authenticate using direct field access
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.userOfficialEmail, 
                loginDto.password
            )
        );

        // 2. FIX: Fetch the UserAuthentication entity from the database using the email
        UserAuthentication user = userRepo.findByUserOfficialEmail(loginDto.userOfficialEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginDto.userOfficialEmail));

        // 3. Generate token passing the correct UserAuthentication entity object
        String token = jwtUtil.generateToken(user);

        // 4. Return a structured response matching your DTO class setup
        AuthResponseDTO authResponse = new AuthResponseDTO(token, "Login successful");

        return ResponseEntity.ok(authResponse);
    }
}