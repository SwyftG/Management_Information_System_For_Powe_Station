package org.eps.common.service;

import javax.annotation.Resource;

import org.eps.hrm.dao.EmployeeDao;
import org.junit.Test;

public class MainTest extends AbstractServiceTest {
	
	@Resource(name = "employeeDao")
	private EmployeeDao employeeDao;
	
	@Test
	public void testCountEmployeeByOrgId() throws Exception {
		System.out.println(employeeDao.countEmployeeByOrgId(1L) == 0L);
	}

}
