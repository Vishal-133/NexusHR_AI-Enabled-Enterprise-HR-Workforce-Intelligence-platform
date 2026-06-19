package com.EMPMANAGE.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EMPMANAGE.DTO.AuthResponseDTO;
import com.EMPMANAGE.DTO.LoginRequestDTO;
import com.EMPMANAGE.DTO.RegisterRequestDTO;
import com.EMPMANAGE.Entity.UserAuthentication;
import com.EMPMANAGE.Repository.userAuthRepository;
import com.EMPMANAGE.Security.JWTUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAuthService {

	
	@Autowired
	private userAuthRepository userAuthRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	
	
	
	public void register(RegisterRequestDTO register) {
		
		if(userAuthRepo.findByUserOfficialEmail(register.userOfficialEmail).isPresent()) {
			throw new RuntimeException("User already exist");
		}
		
		UserAuthentication user= new UserAuthentication();
		
		user.setUserName(register.userName);
		user.setUserOfficialEmail(register.userOfficialEmail);
		user.setPassword(passwordEncoder.encode(register.password));
		user.setRole(register.role);
		
		userAuthRepo.save(user);
	}
	 
	
	public AuthResponseDTO login(LoginRequestDTO login) {
		
		UserAuthentication user=userAuthRepo.findByUserOfficialEmail(login.userOfficialEmail)
				.orElseThrow(()->new RuntimeException("User not Found"));
		
		if(!passwordEncoder.matches(login.password,user.getPassword())) {
			throw new RuntimeException("Invalid Credential");
		}
		
		String token = jwtUtil.generateToken(user);
		return new AuthResponseDTO(token,"Login Successful");
	}
}
