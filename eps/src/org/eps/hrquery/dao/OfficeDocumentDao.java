package org.eps.hrquery.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.ConditionUtils;
import org.eps.hrquery.po.OfficeDocument;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("officeDocumentDao")
@SuppressWarnings("unchecked")
public class OfficeDocumentDao extends GenericTypeDaoHibernateImpl<OfficeDocument, Long> {

	public List<OfficeDocument> findDocumentByCondition(Map<String, Object> condition) {
		StringBuilder hql = new StringBuilder("from OfficeDocument t where 1 = 1");
		
		if (StringUtils.isNotBlank(MapUtils.getString(condition, "beginDate"))) {
			hql.append(" and t.publishDate >= ")
			   .append(ConditionUtils.getBeginDate(MapUtils.getString(condition, "beginDate")));
		}
		
		if (StringUtils.isNotBlank(MapUtils.getString(condition, "endDate"))) {
			hql.append(" and t.publishDate <= ")
			   .append(ConditionUtils.getBeginDate(MapUtils.getString(condition, "endDate")));
		}
		
		if (!MapUtils.getLong(condition, "orgId").equals(-1L)) {
			hql.append(" and t.orgId = ").append(MapUtils.getLong(condition, "orgId"));
		}
		
		if (StringUtils.isNotBlank(MapUtils.getString(condition, "title"))) {
			hql.append(" and t.title = '")
			   .append(MapUtils.getString(condition, "title"))
			   .append("'");
		}
		
		if (StringUtils.isNotBlank(MapUtils.getString(condition, "fileNo"))) {
			hql.append(" and t.fileNo = '")
			   .append(MapUtils.getString(condition, "fileNo"))
			   .append("'");
		}
		
		Query query = getSession().createQuery(hql.toString());
		
		return query.list();
	}
	
}
