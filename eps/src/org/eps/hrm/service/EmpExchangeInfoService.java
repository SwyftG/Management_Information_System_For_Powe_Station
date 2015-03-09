package org.eps.hrm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.util.Constants;
import org.eps.common.util.DateUtils;
import org.eps.common.vo.DataGrid;
import org.eps.hrm.dao.EmpExchangeInfoDao;
import org.eps.hrm.dao.EmployeeDao;
import org.eps.hrm.dao.OrgDao;
import org.eps.hrm.po.EmpExchangeInfo;
import org.eps.hrm.po.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("empExchangeInfoService")
public class EmpExchangeInfoService {
	
	@Resource(name = "empExchangeInfoDao")
	private EmpExchangeInfoDao empExchangeInfoDao;
	
	@Resource(name = "employeeDao")
	private EmployeeDao employeeDao;
	
	@Resource(name = "orgDao")
	private OrgDao orgDao;
	
	public String findAllEmpExchangeInfo() throws Exception {
		List<EmpExchangeInfo> list = wrapper(this.empExchangeInfoDao.findAll());
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	public String findAllPostChangeInfo() throws Exception {
		List<EmpExchangeInfo> list = wrapper(this.empExchangeInfoDao.findAll());
		
		List<EmpExchangeInfo> infos = new ArrayList<EmpExchangeInfo>();
		
		for (EmpExchangeInfo info : list) {
			if (info.getPostChangeFlag().equals(Constants.POST_CHANGE)) {
				infos.add(info);
			}
		}
		
		DataGrid dg = new DataGrid(infos.size(), infos);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addEmpExchangeInfo(EmpExchangeInfo info) throws Exception {
		this.empExchangeInfoDao.add(info);
		
		Employee emp = this.employeeDao.findByPrimaryKey(info.getEmpId());
		
		emp.setOrgId(info.getNewOrgId());
		emp.setPost(info.getNewPost());
		emp.setSalary(info.getNewSalary());
		
		this.employeeDao.update(emp);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeEmpExchangeInfo(Long[] ids) throws Exception {
		this.empExchangeInfoDao.batchDelete(ids);
	}
	
	public EmpExchangeInfo findEmpExchangeInfoById(Long id) throws Exception {
		EmpExchangeInfo info = this.empExchangeInfoDao.findByPrimaryKey(id);
		
		List<EmpExchangeInfo> infos = Arrays.asList(info);
		
		return wrapper(infos).get(0);
	}
	
	private List<EmpExchangeInfo> wrapper(List<EmpExchangeInfo> infos) {
		for (EmpExchangeInfo info : infos) {
			info.setEmpName(this.employeeDao.findByPrimaryKey(info.getEmpId()).getName());
			info.setOldOrg(this.orgDao.findByPrimaryKey(info.getOldOrgId()).getOrgName());
			info.setNewOrg(this.orgDao.findByPrimaryKey(info.getNewOrgId()).getOrgName());
			info.setExchangeDateStr(DateUtils.formatDate(info.getExchangeDate(), "yyyy-MM-dd"));
		}
		
		return infos;
	}

}
