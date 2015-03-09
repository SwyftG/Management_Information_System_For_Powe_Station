package org.eps.common.service;

//import java.util.List;

import javax.annotation.Resource;

import org.eps.common.dao.MenuDao;
import org.junit.Test;

public class MenuDaoTest extends AbstractServiceTest {
	
	@Resource(name = "menuDao")
	private MenuDao menuDao;
	
	@Test
	public void testFindParentIds() throws Exception {
//		Long[] menuIds = { 1L };
		
//		List<Long> ids = this.menuDao.findParentIds(menuIds);
//		
//		System.out.println(ids);
	}

}
