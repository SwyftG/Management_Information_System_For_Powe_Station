package org.eps.auth.dao;

import java.util.List;

import org.eps.auth.po.RoleMenuRelation;
import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.springframework.stereotype.Repository;

@Repository("roleMenuRelationDao")
public class RoleMenuRelationDao extends GenericTypeDaoHibernateImpl<RoleMenuRelation, Long> {

	public void removeRoleMenuRelationByRoleId(Long roleId) {
		String sql = "delete from role_menu_r where role_id = " + roleId;
		
		getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void addRoleMenuRelation(Long roleId, Long[] menuIds) {
		RoleMenuRelation relation = null;
		
		for (Long menuId : menuIds) {
			relation = new RoleMenuRelation();
			
			relation.setRoleId(roleId);
			relation.setMenuId(menuId);
			
			add(relation);
		}
	}
	
	public void addRoleMenuRelation(Long roleId, List<Long> menuIds) {
		Long[] array = menuIds.toArray(new Long[0]);
		
		this.addRoleMenuRelation(roleId, array);
	}
	
}
