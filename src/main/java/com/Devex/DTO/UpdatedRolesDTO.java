package com.Devex.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedRolesDTO {
		private String userId;
//	 private String username;
	    private String fullname;
	    private String email;
	    private String password;
	    private String phone;
	    private String gender;
	    private boolean active;
	    private List<String> roles;	

}
