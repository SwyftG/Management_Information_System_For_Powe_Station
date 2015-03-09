package org.eps.safetyinspection.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.vo.DataGrid;
import org.eps.safetyinspection.dao.SafetyToolDao;
import org.eps.safetyinspection.po.SafetyTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("safetyToolService")
public class SafetyToolService {
	
	@Resource(name = "safetyToolDao")
	private SafetyToolDao safetyToolDao;
	
	@Transactional(rollbackFor = Exception.class)
	public void addSafetyTool(SafetyTool tool) throws Exception {
		this.safetyToolDao.add(tool);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifySafetyTool(SafetyTool tool) throws Exception {
		this.safetyToolDao.update(tool);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeSafetyTool(Long[] ids) throws Exception {
		this.safetyToolDao.batchDelete(ids);
	}
	
	public SafetyTool findSafetyToolById(Long id) throws Exception {
		return this.safetyToolDao.findByPrimaryKey(id);
	}
	
	public String findAllSafetyTool() throws Exception {
		List<SafetyTool> list = this.safetyToolDao.findAllSafetyTool();
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}

}
