package com.cag.twowheeler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cag.twowheeler.entity.User;
import com.cag.twowheeler.exceptation.InvalidUser;
import com.cag.twowheeler.repository.UserRepository;
import com.cag.twowheeler.repository.UserRoleRepository;
import com.cag.twowheeler.security.JwtAuthRequest;
import com.cag.twowheeler.security.JwtAuthResponse;
import com.cag.twowheeler.security.JwtTokenHelper;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	JwtTokenHelper jwtTokenHelper;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;
	

	@PostMapping("/login/userlogin")
	public ResponseEntity<JwtAuthResponse> creatToken(@RequestBody JwtAuthRequest authRequest) throws Exception {
		this.authenticate(authRequest.getUserName(), authRequest.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
		if (userDetails != null) {
			//System.out.println("=============Token Generated===============");
			String token = this.jwtTokenHelper.generateToken(userDetails);
			JwtAuthResponse authResponse = new JwtAuthResponse();
			authResponse.setToken(token);
			authResponse.setUserName(userDetails.getUsername());
			try {
				User user = userRepository.findByUserID(userDetails.getUsername());
				authResponse.setUserrole(user.getRoles());
				if (!user.getStatus().equalsIgnoreCase("ACTIVE")) {
					throw new InvalidUser();
				}
				
			} catch (Exception e) {
				throw new InvalidUser();
			}

			return new ResponseEntity<>(authResponse, HttpStatus.OK);
		}
		// Exception Throw If Invalid User..!
		throw new InvalidUser();

	}

	// check user is valid or not...!
	private void authenticate(String userName, String password) throws Exception {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,
					password);

			authenticationManager.authenticate(authenticationToken);

		} catch (Exception e) {
			throw new InvalidUser();
		}
	}
	
	//=======External purpose=============
	@GetMapping(" ")
	public String passwordCreate(@RequestParam String password){
		return encoder.encode(password);	
	}

}
