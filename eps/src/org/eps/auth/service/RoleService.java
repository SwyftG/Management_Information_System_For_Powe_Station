package org.eps.auth.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.eps.auth.dao.RoleDao;
import org.eps.auth.dao.RoleMenuRelationDao;
import org.eps.auth.po.Role;
import org.eps.common.dao.MenuDao;
import org.eps.common.po.Menu;
import org.eps.common.util.Constants;
import org.eps.common.vo.DataGrid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
public class RoleService {
	
	@Resource(name = "roleDao")
	private RoleDao roleDao;
	
	@Resource(name = "roleMenuRelationDao")
	private RoleMenuRelationDao roleMenuRelationDao;
	
	@Resource(name = "menuDao")
	private MenuDao menuDao;
	
	public String findAllRole() throws Exception {
		List<Role> roles = this.roleDao.findAll();
		
		for (Iterator<Role> iter = roles.iterator(); iter.hasNext();) {
			Role role = iter.next();
			
			if (role.getId().equals(Constants.SUPER_ADMIN_ROLE_ID)) {
				iter.remove();
			}
		}
		
		DataGrid dg = new DataGrid(roles.size(), roles);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void settingRole(Long roleId, Long[] menuIds) throws Exception {
		this.roleMenuRelationDao.removeRoleMenuRelationByRoleId(roleId);
		
		if (ArrayUtils.isNotEmpty(menuIds)) {
			this.roleMenuRelationDao.addRoleMenuRelation(roleId, menuIds);
		}
	}
	
	public Role findRoleById(Long id) throws Exception {
		Role role = this.roleDao.findByPrimaryKey(id);
		
		List<Menu> menus = this.menuDao.findMenuByRoleId(id);
		
		if (CollectionUtils.isNotEmpty(menus)) {
			role.setMenus(menus);
		} else {
			role.setMenus(new ArrayList<Menu>());
		}
		
		return role;
	}

}
