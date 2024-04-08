package com.Devex.Sevice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Devex.Config.CustomUserDetails;
import com.Devex.Entity.User;


@Service
@Transactional
public class UserSecurityService implements UserDetailsService {
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService sessionService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user = userService.findByIdActive(username);
			
			if(user != null) {

				sessionService.set("user", user);
				return new CustomUserDetails(user);
			}
			
			throw new UsernameNotFoundException(username + " not found!");
	}
}
