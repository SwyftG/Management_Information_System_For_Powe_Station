package org.eps.hrm.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.eps.common.action.BaseAction;
import org.eps.common.util.Constants;
import org.eps.common.util.Tools;
import org.eps.common.vo.JsonVO;
import org.eps.hrm.po.EmpSkill;
import org.eps.hrm.service.EmpSkillService;

public class EmpSkillAction extends BaseAction {

	private static final long serialVersionUID = 8895112339202617444L;

	@Resource(name = "empSkillService")
	private EmpSkillService empSkillService;

	private Long id;
	private Long empId;
	private String skillName;
	private Long skillLevel;
	private String certNo;
	private String certUnit;
	private Date acquireTime;
	
	private String selectedIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Long getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(Long skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertUnit() {
		return certUnit;
	}

	public void setCertUnit(String certUnit) {
		this.certUnit = certUnit;
	}

	public Date getAcquireTime() {
		return acquireTime;
	}

	public void setAcquireTime(Date acquireTime) {
		this.acquireTime = acquireTime;
	}
	
	public String getSelectedIds() {
		return selectedIds;
	}
	
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	@Action("listSelectedEmpSkill")
	public void listSelectedEmpSkill() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			PrintWriter out = response.getWriter();

			String json = this.empSkillService.findEmpSkillByEmpId(empId);

			out.write(json);

			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addEmpSkill")
	public void addEmpSkill() {
		JsonVO vo = null;
		PrintWriter out = null;

		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();

			EmpSkill skill = new EmpSkill();
			
			skill.setEmpId(empId);
			skill.setSkillName(skillName);
			skill.setSkillLevel(skillLevel);
			skill.setCertNo(certNo);
			skill.setCertUnit(certUnit);
			skill.setAcquireTime(acquireTime);
			
			this.empSkillService.addEmpSkill(skill);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "新增员工职业技能信息时发生错误！");
		}

		String json = JSONObject.fromObject(vo).toString();

		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("loadEmpSkill")
	public void loadEmpSkill() {
		JsonVO vo = null;
		PrintWriter out = null;

		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();

			EmpSkill skill = this.empSkillService.findEmpSkillById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, skill);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载员工职业技能信息时发生错误！");
		}

		String json = JSONObject.fromObject(vo).toString();

		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("modifyEmpSkill")
	public void modifyEmpSkill() {
		JsonVO vo = null;
		PrintWriter out = null;

		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();

			EmpSkill skill = this.empSkillService.findEmpSkillById(id);
			
			skill.setEmpId(empId);
			skill.setSkillName(skillName);
			skill.setSkillLevel(skillLevel);
			skill.setCertNo(certNo);
			skill.setCertUnit(certUnit);
			skill.setAcquireTime(acquireTime);
			
			this.empSkillService.modifyEmpSkill(skill);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改员工职业技能信息时发生错误！");
		}

		String json = JSONObject.fromObject(vo).toString();

		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("removeEmpSkill")
	public void removeEmpSkill() {
		JsonVO vo = null;
		PrintWriter out = null;

		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();

			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.empSkillService.removeEmpSkill(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除员工职业技能信息时发生错误！");
		}

		String json = JSONObject.fromObject(vo).toString();

		out.write(json);

		IOUtils.closeQuietly(out);
	}

}
