package org.eps.hrm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.Tools;
import org.eps.hrm.po.EmpEvaluate;
import org.eps.hrm.vo.EmpEvaluateVO;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("empEvaluateDao")
@SuppressWarnings("unchecked")
public class EmpEvaluateDao extends GenericTypeDaoHibernateImpl<EmpEvaluate, Long> {
	
	public Long countEmpEvaluateByCondition(Long empId, int evaluateYear) {
		String hql = "select count(id) from EmpEvaluate t where t.empId = :empId and t.evaluateYear = :evaluateYear";
		
		return (Long) getSession().createQuery(hql)
					              .setParameter("empId", empId)
					              .setParameter("evaluateYear", evaluateYear)
					              .uniqueResult();
	}
	
	public List<EmpEvaluateVO> findEmpEvaluateByYear(int year) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT b.evaluate_level, COUNT(b.evaluate_emp_id) num")
		   .append(" FROM empinfo a, emp_evaluate b")
		   .append(" WHERE a.id = b.evaluate_emp_id")
		   .append(" AND b.evaluate_year = ").append(year)
		   .append(" GROUP BY b.evaluate_level");
		
		Query query = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List<Map<String, Object>> maps = query.list();
		
		List<EmpEvaluateVO> vos = new ArrayList<EmpEvaluateVO>();
		
		EmpEvaluateVO vo = null;
		
		for (Map<String, Object> map : maps) {
			vo = new EmpEvaluateVO();
			
			Map<String, Object> m = new CaseInsensitiveMap(map);
			
			vo.setEvaluateLevel(Tools.formatEvaluateLevel(MapUtils.getLong(m, "evaluate_level")));
			vo.setNum(MapUtils.getLong(m, "num"));
			
			vos.add(vo);
		}
		
		return vos;
	}

}
