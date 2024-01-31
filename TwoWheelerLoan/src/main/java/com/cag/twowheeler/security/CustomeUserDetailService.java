package com.cag.twowheeler.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cag.twowheeler.entity.User;
import com.cag.twowheeler.repository.UserRepository;

@Service
public class CustomeUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		// loading user from DataBase...!
		try {
			User user = this.userRepository.findByUserID(username);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	//now go to config to sey we are doing by database authentication
}
