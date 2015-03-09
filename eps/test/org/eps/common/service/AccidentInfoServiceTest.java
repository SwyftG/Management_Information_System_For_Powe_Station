package org.eps.common.service;

import javax.annotation.Resource;

import org.eps.safetyinspection.service.AccidentInfoService;
import org.junit.Test;

public class AccidentInfoServiceTest extends AbstractServiceTest {
	
	@Resource(name = "accidentInfoService")
	private AccidentInfoService accidentInfoService;
	
	@Test
	public void testGenerateChartXml() throws Exception {
		String xml = accidentInfoService.generateChartXml();
		
		System.out.println(xml);
	}

}
