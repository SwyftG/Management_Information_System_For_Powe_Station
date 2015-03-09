package org.eps.hrm.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.hrm.po.Employee;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("employeeDao")
@SuppressWarnings("unchecked")
public class EmployeeDao extends GenericTypeDaoHibernateImpl<Employee, Long> {
	
	public void removeEmployee(Long[] ids) {
		for (Long id : ids) {
			// 删除员工关联信息
			getSession().createSQLQuery("delete from emp_skill where emp_id = " + id).executeUpdate();
			getSession().createSQLQuery("delete from emp_exchange_info where emp_id = " + id).executeUpdate();
			getSession().createSQLQuery("delete from emp_evaluate where evaluate_emp_id = " + id).executeUpdate();
			
			// 删除员工信息
			delete(findByPrimaryKey(id));
		}
	}
	
	public Long countEmployeeByOrgId(Long orgId) {
		String hql = "select count(t.id) from Employee t where t.orgId = :orgId";
		
		Query query = getSession().createQuery(hql).setParameter("orgId", orgId);
		
		return (Long) query.uniqueResult();
	}
	
	public List<Employee> findEmployeeByCondition(String personCode, String name, Long orgId) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("from Employee t where 1 = 1");
		
		if (StringUtils.isNotBlank(personCode)) {
			hql.append(" and t.personCode = '")
			   .append(personCode)
			   .append("'");
		}
		
		if (StringUtils.isNotBlank(name)) {
			hql.append(" and t.name = '")
			   .append(name)
			   .append("'");
		}
		
		if (!orgId.equals(-1L)) {
			hql.append(" and t.orgId = ").append(orgId);
		}
		
		return getHibernateTemplate().find(hql.toString());
	}

}
