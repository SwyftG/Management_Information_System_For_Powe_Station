package org.eps.safetyinspection.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.vo.DataGrid;
import org.eps.safetyinspection.dao.FireEquipmentDao;
import org.eps.safetyinspection.po.FireEquipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("fireEquipmentService")
public class FireEquipmentService {
	
	@Resource(name = "fireEquipmentDao")
	private FireEquipmentDao fireEquipmentDao;
	
	@Transactional(rollbackFor = Exception.class)
	public void addFireEquipment(FireEquipment equipment) throws Exception {
		this.fireEquipmentDao.add(equipment);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyFireEquipment(FireEquipment equipment) throws Exception {
		this.fireEquipmentDao.update(equipment);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeFireEquipment(Long[] ids) throws Exception {
		this.fireEquipmentDao.batchDelete(ids);
	}
	
	public FireEquipment findFireEquipmentById(Long id) throws Exception {
		return this.fireEquipmentDao.findByPrimaryKey(id);
	}
	
	public String findAllEquipment() throws Exception {
		List<FireEquipment> list = this.fireEquipmentDao.findAllFireEquipment();
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}

}
