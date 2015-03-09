package org.eps.common.dao;

import java.util.List;

import org.eps.common.po.Menu;
import org.eps.common.po.UserInfo;
import org.springframework.stereotype.Repository;

@Repository("menuDao")
@SuppressWarnings("unchecked")
public class MenuDao extends GenericTypeDaoHibernateImpl<Menu, Long> {
	
	public List<Menu> findCurrentUserMenus(UserInfo user) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select a from Menu a, Role b, RoleMenuRelation c")
		   .append(" where b.id = c.roleId")
		   .append(" and a.id = c.menuId")
		   .append(" and b.id = :roleId");
		
		return getSession().createQuery(hql.toString())
						   .setParameter("roleId", user.getUserLevel())
						   .list();
		
	}
	
	public List<Menu> findMenuByRoleId(Long roleId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("select b")
		   .append(" from Role a,")
		   .append("      Menu b,")
		   .append("      RoleMenuRelation c")
		   .append(" where a.id = c.roleId")
		   .append(" and b.id = c.menuId")
		   .append(" and a.id = ?");
		
		return getHibernateTemplate().find(hql.toString(), roleId);
	}
	
}
