package org.eps.hrm.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.eps.common.util.Constants;
import org.eps.common.vo.DataGrid;
import org.eps.hrm.dao.EmployeeDao;
import org.eps.hrm.dao.OrgDao;
import org.eps.hrm.po.Org;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orgService")
public class OrgService {
	
	@Resource(name = "orgDao")
	private OrgDao orgDao;
	
	@Resource(name = "employeeDao")
	private EmployeeDao employeeDao;
	
	public String findAllOrg() throws Exception {
		List<Org> orgs = this.orgDao.findAllOrg();
		
		DataGrid dg = new DataGrid(orgs.size(), orgs);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addOrg(Org org) throws Exception {
		this.orgDao.add(org);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyOrg(Org org) throws Exception {
		this.orgDao.update(org);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeOrg(Long[] ids) throws Exception {
		for (Long id : ids) {
			Long count = this.employeeDao.countEmployeeByOrgId(id);
			
			if (0L != count) {
				throw new Exception(this.orgDao.findByPrimaryKey(id).getOrgName() + "机构下还有员工信息，不能删除该机构！");
			}
		}
		
		this.orgDao.batchDelete(ids);
	}
	
	public Org findOrgById(Long id) throws Exception {
		Org org = this.orgDao.findByPrimaryKey(id);
		
		if (!org.getParentId().equals(Constants.DEFAULT_SUPER_ID)) {
			Org superOrg = this.orgDao.findByPrimaryKey(org.getParentId());
			
			org.setParentOrgName(superOrg.getOrgName());
		} else {
			org.setParentOrgName(StringUtils.EMPTY);
		}
		
		return org;
	}

}
