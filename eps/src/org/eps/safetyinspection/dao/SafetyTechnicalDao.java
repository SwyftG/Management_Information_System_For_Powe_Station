package org.eps.safetyinspection.dao;

import java.util.List;

import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.DateUtils;
import org.eps.safetyinspection.po.SafetyTechnical;
import org.springframework.stereotype.Repository;

@Repository("safetyTechnicalDao")
public class SafetyTechnicalDao extends GenericTypeDaoHibernateImpl<SafetyTechnical, Long> {

	public List<SafetyTechnical> findAllSafetyTechnical() {
		return wrapper(findAll());
	}

	private List<SafetyTechnical> wrapper(List<SafetyTechnical> sts) {
		for (SafetyTechnical st : sts) {
			st.setCreateTimeStr(DateUtils.formatDate(st.getCreateTime()));
		}
		
		return sts;
	}
	
}
