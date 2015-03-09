package org.eps.safetyinspection.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.vo.DataGrid;
import org.eps.safetyinspection.dao.VehicleSafetyDao;
import org.eps.safetyinspection.po.VehicleSafety;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("vehicleSafetyService")
public class VehicleSafetyService {
	
	@Resource(name = "vehicleSafetyDao")
	private VehicleSafetyDao vehicleSafetyDao;
	
	@Transactional(rollbackFor = Exception.class)
	public void addVehicleSafety(VehicleSafety safety) throws Exception {
		this.vehicleSafetyDao.add(safety);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyVehicleSafety(VehicleSafety safety) throws Exception {
		this.vehicleSafetyDao.update(safety);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeVehicleSafety(Long[] ids) throws Exception {
		this.vehicleSafetyDao.batchDelete(ids);
	}
	
	public VehicleSafety findVehicleSafetyById(Long id) throws Exception {
		return this.vehicleSafetyDao.findByPrimaryKey(id);
	}
	
	public String findAllVehicleSafety() throws Exception {
		List<VehicleSafety> list = this.vehicleSafetyDao.findAllVehicleSafety();
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}

}
