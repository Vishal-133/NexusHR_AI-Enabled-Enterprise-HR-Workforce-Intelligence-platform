package com.EMPMANAGE.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoginRequestDTO {

	
	public String userOfficialEmail;
	public String password;
	
}
