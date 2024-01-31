package com.cag.twowheeler.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRegister {
	
	private String emailID;
	private String userName;
	private String userID;
	private String password;
	private int otp;
	private List<String> roles;

}
