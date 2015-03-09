package org.eps.safetyinspection.dao;

import java.util.List;

import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.DateUtils;
import org.eps.safetyinspection.po.SafetyTool;
import org.springframework.stereotype.Repository;

@Repository("safetyToolDao")
public class SafetyToolDao extends GenericTypeDaoHibernateImpl<SafetyTool, Long> {

	public List<SafetyTool> findAllSafetyTool() {
		return wrapper(findAll());
	}

	private List<SafetyTool> wrapper(List<SafetyTool> tools) {
		for (SafetyTool tool : tools) {
			tool.setCreateTimeStr(DateUtils.formatDate(tool.getCreateTime()));
		}
		
		return tools;
	}
	
}
