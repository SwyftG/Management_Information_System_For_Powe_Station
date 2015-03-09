package org.eps.safetyinspection.dao;

import java.util.ArrayList;
import java.util.List;

import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.DateUtils;
import org.eps.safetyinspection.po.VehicleSafety;
import org.springframework.stereotype.Repository;

@Repository("vehicleSafetyDao")
public class VehicleSafetyDao extends GenericTypeDaoHibernateImpl<VehicleSafety, Long> {
	
	public List<VehicleSafety> findAllVehicleSafety() {	
		return wrapper(findAll());
	}

	private List<VehicleSafety> wrapper(List<VehicleSafety> vehicleSafeties) {
		List<VehicleSafety> list = new ArrayList<VehicleSafety>();
		
		for (VehicleSafety vehicleSafety : vehicleSafeties) {
			vehicleSafety.setInspectionDateStr(DateUtils.formatDate(vehicleSafety.getInspectionDate(), "yyyy-MM-dd"));
			vehicleSafety.setInspectionDate(null);
			
			list.add(vehicleSafety);
		}
		
		return list;
	}

}
