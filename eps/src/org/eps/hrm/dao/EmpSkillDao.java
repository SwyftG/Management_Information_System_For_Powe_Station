package org.eps.hrm.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.DateUtils;
import org.eps.common.util.Tools;
import org.eps.hrm.po.EmpSkill;
import org.eps.hrm.vo.EmpSkillVO;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("empSkillDao")
@SuppressWarnings("unchecked")
public class EmpSkillDao extends GenericTypeDaoHibernateImpl<EmpSkill, Long> {
	
	public List<EmpSkill> findEmpSkillByEmpId(Long empId) {
		String hql = "from EmpSkill t where t.empId = ?";
		
		List<EmpSkill> skills = getHibernateTemplate().find(hql, empId);
		
		return wrapper(skills); 
	}
	
	public List<EmpSkillVO> findEmpSkillByCondition(String personCode, String name, Long orgId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT")
		   .append("	a.personcode personCode,")
		   .append("	a.NAME personName,")
		   .append("	c.org_name orgName,")
		   .append("	b.skill_name skillName,")
		   .append("	(CASE b.skill_level")
		   .append("		WHEN 1 THEN '一级'")
		   .append("		WHEN 2 THEN '二级'")
		   .append("		WHEN 3 THEN '三级'")
		   .append("		WHEN 4 THEN '四级'")
		   .append("		WHEN 5 THEN '五级' END) skillLevel,")
		   .append("	b.cert_no certNo,")
		   .append("	b.cert_unit certUnit,")
		   .append("	CAST(DATE_FORMAT(b.acquire_time, '%Y-%m-%d') AS CHAR) acquireTime")
		   .append(" FROM empinfo a, emp_skill b, org c")
		   .append(" WHERE a.id = b.emp_id")
		   .append(" AND a.orgid = c.id");
		
		if (StringUtils.isNotBlank(personCode)) {
			sql.append(" AND a.personcode = '")
			   .append(personCode)
			   .append("'");
		}
		
		if (StringUtils.isNotBlank(name)) {
			sql.append(" AND a.name = '")
			   .append(name)
			   .append("'");
		}
		
		if (!orgId.equals(-1L)) {
			sql.append(" AND c.id = ").append(orgId);
		}
		
		return getSession().createSQLQuery(sql.toString())
				           .setResultTransformer(Transformers.aliasToBean(EmpSkillVO.class))
				           .list();
	}

	private List<EmpSkill> wrapper(List<EmpSkill> skills) {
		for (EmpSkill skill : skills) {
			skill.setAcquireTimeStr(DateUtils.formatDate(skill.getAcquireTime(), "yyyy-MM-dd"));
			skill.setSkillLevelStr(Tools.formatSkillLevel(skill.getSkillLevel()));
		}
		
		return skills;
	}

}
