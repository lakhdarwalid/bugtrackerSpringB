package com.bugtracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServ implements UserDetailsService{
	@Autowired
	UserRepo userRep;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRep.findByUserName(username)
				.orElseThrow(()-> new UsernameNotFoundException("Not found "+username));
		//user.orElseThrow(()-> new UsernameNotFoundException("Not found "+username));

		return new UserDet(user);//user.map(UserDet::new).get();
	}
	
	
	

}
