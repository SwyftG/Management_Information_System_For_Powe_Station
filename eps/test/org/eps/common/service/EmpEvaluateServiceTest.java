package org.eps.common.service;

import javax.annotation.Resource;

import org.eps.hrm.service.EmpEvaluateService;
import org.junit.Test;

public class EmpEvaluateServiceTest extends AbstractServiceTest {
	
	@Resource(name = "empEvaluateService")
	private EmpEvaluateService empEvaluateService;
	
	@Test
	public void testGenerateEmpEvaluateChartXml() throws Exception {
		String xml = this.empEvaluateService.generateEmpEvaluateChartXml(2013);
		
		System.out.println(xml);
	}

}
