package org.eps.common.service;

import javax.annotation.Resource;

import org.eps.hrm.service.EmpRewardInfoService;
import org.junit.Test;

public class EmpRewardInfoServiceTest extends AbstractServiceTest {
	
	@Resource(name = "empRewardInfoService")
	private EmpRewardInfoService empRewardInfoService;
	
	@Test
	public void testMethod() throws Exception {
		String json = this.empRewardInfoService.findAllEmpRewardInfo();
		
		System.out.println(json);
	}

}
