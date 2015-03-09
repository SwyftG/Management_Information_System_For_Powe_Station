package org.eps.common.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.eps.common.po.UserInfo;
import org.springframework.stereotype.Repository;

@Repository("userInfoDao")
@SuppressWarnings("unchecked")
public class UserInfoDao extends GenericTypeDaoHibernateImpl<UserInfo, Long> {

	public UserInfo findUserByUsername(String username) {
		String hql = "from UserInfo t where t.username = ?";
		
		List<UserInfo> users = getHibernateTemplate().find(hql, username);
		
		return CollectionUtils.isNotEmpty(users) ? users.get(0) : null;
	}
	
}
