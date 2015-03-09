package org.eps.device.dao;

import java.util.List;

import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.DateUtils;
import org.eps.device.po.DeviceInfo;
import org.springframework.stereotype.Repository;

@Repository("deviceInfoDao")
@SuppressWarnings("unchecked")
public class DeviceInfoDao extends GenericTypeDaoHibernateImpl<DeviceInfo, Long> {
	
	public List<DeviceInfo> findDevicesByType(long type) {
		String hql = "from DeviceInfo t where t.deviceType = ?";
		
		List<DeviceInfo> devices = getHibernateTemplate().find(hql, type);
		
		return wrapper(devices);
	}

	private List<DeviceInfo> wrapper(List<DeviceInfo> devices) {
		for (DeviceInfo device : devices) {
			device.setCreateTimeStr(DateUtils.formatDate(device.getCreateTime()));
		}
		
		return devices;
	}
	
}
