package org.eps.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.eps.common.dao.MenuDao;
import org.eps.common.po.Menu;
import org.eps.common.po.UserInfo;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MenuService {
	
	@Resource(name = "menuDao")
	private MenuDao menuDao;
	
	public List<Menu> findCurrentUserMenus(UserInfo user) throws Exception {
		return this.menuDao.findCurrentUserMenus(user);
	}

}
