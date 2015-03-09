package org.eps.hrm.dao;

import java.util.List;

import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.hrm.po.EmpRewardInfo;
import org.eps.hrm.vo.EmpRewardVO;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("empRewardInfoDao")
@SuppressWarnings("unchecked")
public class EmpRewardInfoDao extends GenericTypeDaoHibernateImpl<EmpRewardInfo, Long> {
	
	public List<EmpRewardVO> findAllEmpRewardInfo() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT CAST(b.id AS CHAR) id,a.name empName, c.org_name orgName, a.post post,")
		   .append("(CASE b.reward_type WHEN 0 THEN '½±Àø' WHEN 1 THEN '³Í·£' END) rewardType,")
		   .append("b.reward_reason rewardReason, CAST(DATE_FORMAT(b.reward_time, '%Y-%m-%d %H:%i:%s') AS CHAR) rewardTime,")
		   .append("b.reward_money rewardMoney")
		   .append(" FROM empinfo a, emp_reward_info b, org c")
		   .append(" WHERE a.id = b.emp_id")
		   .append(" AND a.orgid = c.id");
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.aliasToBean(EmpRewardVO.class));
		
		return query.list();
	}
	
	public EmpRewardVO findEmpRewardInfoById(Long id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT CAST(b.id AS CHAR) id,a.name empName, c.org_name orgName, a.post post,")
		   .append("(CASE b.reward_type WHEN 0 THEN '½±Àø' WHEN 1 THEN '³Í·£' END) rewardType,")
		   .append("b.reward_reason rewardReason, CAST(DATE_FORMAT(b.reward_time, '%Y-%m-%d %H:%i:%s') AS CHAR) rewardTime,")
		   .append("b.reward_money rewardMoney")
		   .append(" FROM empinfo a, emp_reward_info b, org c")
		   .append(" WHERE a.id = b.emp_id")
		   .append(" AND a.orgid = c.id")
		   .append(" AND b.id = ").append(id);
		
		Query query = getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.aliasToBean(EmpRewardVO.class));
		
		return (EmpRewardVO) query.list().get(0);
	}

}
