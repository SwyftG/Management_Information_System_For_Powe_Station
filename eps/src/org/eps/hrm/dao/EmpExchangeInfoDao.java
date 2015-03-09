package org.eps.hrm.dao;

import org.eps.common.dao.GenericTypeDaoHibernateImpl;
import org.eps.hrm.po.EmpExchangeInfo;
import org.springframework.stereotype.Repository;

@Repository("empExchangeInfoDao")
public class EmpExchangeInfoDao extends GenericTypeDaoHibernateImpl<EmpExchangeInfo, Long> {
	
}
