package org.eps.auth.dao;

import org.eps.auth.po.Role;
import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDao extends GenericTypeDaoHibernateImpl<Role, Long> {

}
