package org.eps.safetyinspection.dao;

import java.util.List;

import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.common.util.DateUtils;
import org.eps.safetyinspection.po.FireEquipment;
import org.springframework.stereotype.Repository;

@Repository("fireEquipmentDao")
public class FireEquipmentDao extends GenericTypeDaoHibernateImpl<FireEquipment, Long> {
	
	public List<FireEquipment> findAllFireEquipment() {
		return wrapper(findAll());
	}

	private List<FireEquipment> wrapper(List<FireEquipment> fireEquipments) {
		for (FireEquipment fireEquipment : fireEquipments) {
			fireEquipment.setCreateTimeStr(DateUtils.formatDate(fireEquipment.getCreateTime()));
		}
		
		return fireEquipments;
	}

}
