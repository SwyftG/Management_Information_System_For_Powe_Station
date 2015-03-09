package org.eps.hrm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.eps.common.util.Constants;
import org.eps.common.util.DateUtils;
import org.eps.common.vo.DataGrid;
import org.eps.hrm.dao.EmployeeDao;
import org.eps.hrm.dao.OrgDao;
import org.eps.hrm.po.Employee;
import org.eps.hrm.vo.EmpInfoTreeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("employeeService")
public class EmployeeService {
	
	@Resource(name = "employeeDao")
	private EmployeeDao employeeDao;
	
	@Resource(name = "orgDao")
	private OrgDao orgDao;
	
	public String findAllEmployee() throws Exception {
		List<Employee> employees = wrapper(this.employeeDao.findAll());
		
		DataGrid dg = new DataGrid(employees.size(), employees);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void addEmployee(Employee employee) throws Exception {
		this.employeeDao.add(employee);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modifyEmployee(Employee employee) throws Exception {
		this.employeeDao.update(employee);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void removeEmployee(Long[] ids) throws Exception {
		this.employeeDao.removeEmployee(ids);
	}
	
	public Employee findEmployeeById(Long id) throws Exception {
		Employee emp = this.employeeDao.findByPrimaryKey(id);
		
		List<Employee> list = Arrays.asList(emp);
		
		return wrapper(list).get(0);
	}
	
	public List<EmpInfoTreeVO> getEmpInfoTreeData() throws Exception {
		List<EmpInfoTreeVO> list = new ArrayList<EmpInfoTreeVO>();
		
		EmpInfoTreeVO vo = null;
		
		List<Employee> emps = this.employeeDao.findAll();
		
		for (Employee emp : emps) {
			vo = new EmpInfoTreeVO();
			
			vo.setId(emp.getId());
			vo.setName(emp.getName());
			vo.setPid(Constants.DEFAULT_SUPER_ID);
			
			list.add(vo);
		}
		
		return list;
	}
	
	public String findEmployeeByCondition(String personCode, String name, Long orgId) throws Exception {
		List<Employee> list = wrapper(this.employeeDao.findEmployeeByCondition(personCode, name, orgId));
		
		DataGrid dg = new DataGrid(list.size(), list);
		
		return JSONObject.fromObject(dg).toString();
	}
	
	private List<Employee> wrapper(List<Employee> employees) {
		for (Employee emp : employees) {
			emp.setBirthdayStr(DateUtils.formatDate(emp.getBirthday(), "yyyy-MM-dd"));
			emp.setOrgName(orgDao.findByPrimaryKey(emp.getOrgId()).getOrgName());
		}
		
		return employees;
	}

}
