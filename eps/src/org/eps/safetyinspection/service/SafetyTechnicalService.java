package org.eps.safetyinspection.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.vo.DataGrid;
import org.eps.safetyinspection.dao.SafetyTechnicalDao;
import org.eps.safetyinspection.po.SafetyTechnical;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("safetyTechnicalService")
public class SafetyTechnicalService {
	
	@Resource(name = "safetyTechnicalDao")
	private SafetyTechnicalDao safetyTechnicalDao;
	
	@Transactional(rollbackFor = Exception.class)
	public void addSafetyTechnical(SafetyTechnical st) throws Exception {
		this.safetyTechnicalDao.add(st);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifySafetyTechnical(SafetyTechnical st) throws Exception {
		this.safetyTechnicalDao.update(st);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeSafetyTechnical(Long[] ids) throws Exception {
		this.safetyTechnicalDao.batchDelete(ids);
	}
	
	public SafetyTechnical findSafetyTechnicalById(Long id) throws Exception {
		return this.safetyTechnicalDao.findByPrimaryKey(id);
	}
	
	public String findAllSafetyTechnical() throws Exception {
		List<SafetyTechnical> list = this.safetyTechnicalDao.findAllSafetyTechnical();
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}

}
