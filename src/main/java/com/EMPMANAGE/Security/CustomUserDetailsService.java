package com.EMPMANAGE.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.EMPMANAGE.Entity.UserAuthentication;
import com.EMPMANAGE.Enum.Permission;
import com.EMPMANAGE.Repository.userAuthRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private userAuthRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String userofficialEmail) throws UsernameNotFoundException {
		
		UserAuthentication user = userRepo.findByUserOfficialEmail(userofficialEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userofficialEmail));
		
		// 1. Create an authority list for Spring Security
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		// 2. Add the User's main Role (prefixed with ROLE_ as per standard Spring Security practice)
		if (user.getRole() != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
		}
		
		// 3. Map your custom Permissions into GrantedAuthority objects
		Set<Permission> permissions = RoleBasedPermission.getRoleBasedPermission().get(user.getRole());
		if (permissions != null) {
			for (Permission perm : permissions) {
				authorities.add(new SimpleGrantedAuthority(perm.name()));
			}
		}
	
		// FIXED: Pass the real authorities list instead of an empty list, and removed invisible whitespaces
		return new org.springframework.security.core.userdetails.User(
				user.getUserOfficialEmail(),
				user.getPassword(),
				authorities
		);
	}
}