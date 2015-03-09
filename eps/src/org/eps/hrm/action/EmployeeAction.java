package org.eps.hrm.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.eps.common.action.BaseAction;
import org.eps.common.util.Constants;
import org.eps.common.util.Tools;
import org.eps.common.vo.JsonVO;
import org.eps.hrm.po.Employee;
import org.eps.hrm.service.EmployeeService;

public class EmployeeAction extends BaseAction {

	private static final long serialVersionUID = -4845923744636328636L;

	@Resource(name = "employeeService")
	private EmployeeService employeeService;

	private Long id;
	private String name;
	private int age;
	private Date birthday;
	private String gender;
	private Long orgId;
	private String post;
	private double salary;

	private String selectedIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	public String getPost() {
		return post;
	}
	
	public void setPost(String post) {
		this.post = post;
	}

	@Action("listAllEmployee")
	public void listAllEmployee() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			PrintWriter out = response.getWriter();

			String json = this.employeeService.findAllEmployee();

			out.write(json);

			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Action("addEmployee")
	public void addEmployee() {
		JsonVO vo = null;
		PrintWriter out = null;

		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();

			Employee emp = new Employee();
			
			emp.setPersonCode(RandomStringUtils.random(6, true, true));
			emp.setName(name);
			emp.setAge(age);
			emp.setBirthday(birthday);
			emp.setGender(gender);
			emp.setOrgId(orgId);
			emp.setSalary(salary);
			emp.setPost(post);
			
			this.employeeService.addEmployee(emp);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "新增员工信息时发生错误！");
		}

		String json = JSONObject.fromObject(vo).toString();

		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("loadEmployee")
	public void loadEmployee() {
		JsonVO vo = null;
		PrintWriter out = null;

		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();

			Employee emp = this.employeeService.findEmployeeById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, emp);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载员工信息时发生错误！");
		}

		String json = JSONObject.fromObject(vo).toString();

		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("modifyEmployee")
	public void modifyEmployee() {
		JsonVO vo = null;
		PrintWriter out = null;

		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();

			Employee emp = this.employeeService.findEmployeeById(id);
			
			emp.setName(name);
			emp.setAge(age);
			emp.setBirthday(birthday);
			emp.setGender(gender);
			emp.setOrgId(orgId);
			emp.setSalary(salary);
			emp.setPost(post);
			
			this.employeeService.modifyEmployee(emp);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改员工信息时发生错误！");
		}

		String json = JSONObject.fromObject(vo).toString();

		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("removeEmployee")
	public void removeEmployee() {
		JsonVO vo = null;
		PrintWriter out = null;

		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();

			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);

			this.employeeService.removeEmployee(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除员工信息时发生错误！");
		}

		String json = JSONObject.fromObject(vo).toString();

		out.write(json);

		IOUtils.closeQuietly(out);
	}

}
