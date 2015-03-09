package org.eps.common.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.eps.common.po.Menu;
import org.eps.common.po.UserInfo;
import org.eps.common.vo.Tree;
import org.junit.Test;

public class TreeServiceTest extends AbstractServiceTest {
	
	@Resource(name = "treeService")
	private TreeService treeService;
	
	@Resource(name = "userInfoService")
	private UserInfoService userInfoService;
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	@Test
	public void testGetSystemMenuTree() throws Exception {
		UserInfo user = this.userInfoService.findUserByUsername("admin");
		
		List<Menu> menus = this.menuService.findCurrentUserMenus(user);
		
		Tree tree = this.treeService.getSystemMenuTree(menus);
		
		String json = JSONArray.fromObject(tree).toString();
		
		System.out.println(json);
	}

}
