package org.eps.common.service;

import org.eps.common.dao.UserInfoDao;
import org.eps.common.po.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userInfoService")
public class UserInfoService {
	
	@Autowired
	private UserInfoDao userInfoDao;

	public UserInfo findUserByUsername(String username) throws Exception {
		return this.userInfoDao.findUserByUsername(username);
	}
	
}
