package com.EMPMANAGE.Security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.EMPMANAGE.Enum.Permission;
import com.EMPMANAGE.Enum.Role;

public class RoleBasedPermission {

	
	public static Map<Role,Set<Permission>>getRoleBasedPermission(){
		
		Map<Role,Set<Permission>> map =new HashMap<>();
		
		map.put(Role.ADMIN, new HashSet<>(Arrays.asList(Permission.CREATE_EMP,
														Permission.UPDATE_EMP,
														Permission.DELETE_EMP,
														Permission.VIEW_EMP,
														Permission.RUN_PAYROLE,
														Permission.VIEW_PAYROLL,
														Permission.APPROVE_LEAVE,
														Permission.VIEW_ANALYTICS)));
		
		
		map.put(Role.HR, new HashSet<>(Arrays.asList(Permission.CREATE_EMP,
													 Permission.UPDATE_EMP,
													 Permission.VIEW_EMP,
													 Permission.RUN_PAYROLE,
													 Permission.VIEW_PAYROLL,
													 Permission.APPROVE_LEAVE)));
		
		map.put(Role.EMPLOYEE, new HashSet<>(Arrays.asList(Permission.APPLY_LEAVE)));
		
		return map;
		
	}
}
