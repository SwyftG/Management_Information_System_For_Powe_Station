package org.eps.device.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.vo.DataGrid;
import org.eps.device.dao.DeviceInfoDao;
import org.eps.device.po.DeviceInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("deviceInfoService")
public class DeviceInfoService {
	
	@Resource(name = "deviceInfoDao")
	private DeviceInfoDao deviceInfoDao;
	
	public String findDevicesByType(long type) {
		List<DeviceInfo> devices = this.deviceInfoDao.findDevicesByType(type);
		
		DataGrid dg = new DataGrid(devices.size(), devices);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addDeviceInfo(DeviceInfo device) throws Exception {
		this.deviceInfoDao.add(device);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyDeviceInfo(DeviceInfo device) throws Exception {
		this.deviceInfoDao.update(device);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeDeviceInfo(Long[] ids) throws Exception {
		this.deviceInfoDao.batchDelete(ids);
	}
	
	public DeviceInfo findDeviceInfoById(long id) throws Exception {
		return this.deviceInfoDao.findByPrimaryKey(id);
	}

}
