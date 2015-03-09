package org.eps.common.security;

import javax.annotation.Resource;

import org.eps.common.po.UserInfo;
import org.eps.common.service.UserInfoService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EpsUserDetailsService implements UserDetailsService {
	
	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfo user = null;
		
		try {
			user = this.userInfoService.findUserByUsername(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		return user;
	}

}
