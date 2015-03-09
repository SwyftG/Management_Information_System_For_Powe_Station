package org.eps.hrm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;
import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.hrm.po.Org;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("orgDao")
@SuppressWarnings("unchecked")
public class OrgDao extends GenericTypeDaoHibernateImpl<Org, Long> {
	
	public List<Org> findAllOrg() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT t.id, t.org_name, t.org_desc, t.org_pid,")
		   .append("(SELECT org_name FROM org WHERE id = t.org_pid AND t.id <> -1) super_org")
		   .append(" FROM org t");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List<Org> orgs = new ArrayList<Org>();
		
		Org org = null;
		
		List<Map<String, Object>> maps = query.list();
		
		for (Map<String, Object> map : maps) {
			Map<String, Object> m = new CaseInsensitiveMap(map);
			
			org = new Org();
			
			org.setId(MapUtils.getLong(m, "id"));
			org.setOrgName(MapUtils.getString(m, "org_name", StringUtils.EMPTY));
			org.setOrgDesc(MapUtils.getString(m, "org_desc", StringUtils.EMPTY));
			org.setParentId(MapUtils.getLong(m, "org_pid"));
			org.setParentOrgName(MapUtils.getString(m, "super_org", StringUtils.EMPTY));
			
			orgs.add(org);
		}
		
		return orgs;
	}

}
