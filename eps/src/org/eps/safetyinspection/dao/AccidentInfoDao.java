package org.eps.safetyinspection.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;
import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.DateUtils;
import org.eps.safetyinspection.po.AccidentInfo;
import org.eps.safetyinspection.vo.AccidentInfoVO;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository("accidentInfoDao")
@SuppressWarnings("unchecked")
public class AccidentInfoDao extends GenericTypeDaoHibernateImpl<AccidentInfo, Long> {
	
	public List<AccidentInfo> findAllAccidentInfo() {
		return wrapper(findAll());
	}

	private List<AccidentInfo> wrapper(List<AccidentInfo> infos) {
		for (AccidentInfo info : infos) {
			info.setAccidentTimeStr(DateUtils.formatDate(info.getAccidentTime(), "yyyy-MM-dd"));
		}
		
		return infos;
	}
	
	public Long getMinYear() {
		String sql = "SELECT IFNULL(MIN(CAST(DATE_FORMAT(t.accident_time, '%Y') AS CHAR)), 0) FROM accident_info t";
		
		Query query = getSession().createSQLQuery(sql);
		
		return Long.valueOf((String) query.uniqueResult());
	}
	
	public Long getMaxYear() {
		String sql = "SELECT IFNULL(MAX(CAST(DATE_FORMAT(t.accident_time, '%Y') AS CHAR)), 0) FROM accident_info t";
		
		Query query = getSession().createSQLQuery(sql);
		
		return Long.valueOf((String) query.uniqueResult());
	}
	
	public List<AccidentInfoVO> findAccidentInfo2Chart() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT CAST(DATE_FORMAT(t.accident_time, '%Y') AS CHAR) YEAR, COUNT(t.id) num")
		   .append(" FROM accident_info t")
		   .append(" GROUP BY CAST(DATE_FORMAT(t.accident_time, '%Y') AS CHAR)");
		
		Query query = getSession().createSQLQuery(sql.toString());
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List<Map<String, Object>> maps = query.list();
		
		List<AccidentInfoVO> vos = new ArrayList<AccidentInfoVO>();
		
		AccidentInfoVO vo = null;
		
		for (Map<String, Object> map : maps) {
			vo = new AccidentInfoVO();
			
			Map<String, Object> m = new CaseInsensitiveMap(map);
			
			vo.setAccidentYear(MapUtils.getString(m, "year", StringUtils.EMPTY));
			vo.setAccidentNum(MapUtils.getLong(m, "num"));
			
			vos.add(vo);
		}
		
		return vos;
	}
	
}
