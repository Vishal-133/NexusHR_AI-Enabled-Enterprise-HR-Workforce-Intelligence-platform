package com.EMPMANAGE.DTO;

import com.EMPMANAGE.Enum.Role;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class RegisterRequestDTO {
	
	public String userName;
	public String userOfficialEmail;
	public String password;
	public Role role;

}
