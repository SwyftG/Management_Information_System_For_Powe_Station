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
import org.eps.hrm.po.EmpRewardInfo;
import org.eps.hrm.service.EmpRewardInfoService;
import org.eps.hrm.vo.EmpRewardVO;

public class EmpRewardInfoAction extends BaseAction {

	private static final long serialVersionUID = -4207084011072734214L;

	@Resource(name = "empRewardInfoService")
	private EmpRewardInfoService empRewardInfoService;

	private Long id;
	private Long empId;
	private Long rewardType;
	private String reason;
	private Double rewardMoney;

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

	public Long getRewardType() {
		return rewardType;
	}

	public void setRewardType(Long rewardType) {
		this.rewardType = rewardType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Double getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(Double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	@Action("listAllEmpRewardInfo")
	public void listAllEmpRewardInfo() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.empRewardInfoService.findAllEmpRewardInfo();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addEmpRewardInfo")
	public void addEmpRewardInfo() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			EmpRewardInfo info = new EmpRewardInfo();
			
			info.setEmpId(empId);
			info.setRewardType(rewardType);
			info.setReason(reason);
			info.setRewardTime(new Date());
			info.setRewardMoney(rewardMoney);
			
			this.empRewardInfoService.addEmpRewardInfo(info);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "新增员工奖惩信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("loadEmpRewardInfo")
	public void loadEmpRewardInfo() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			EmpRewardVO info = this.empRewardInfoService.findEmpRewardInfoById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, info);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载员工奖惩信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("modifyEmpRewardInfo")
	public void modifyEmpRewardInfo() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			EmpRewardInfo info = this.empRewardInfoService.findInfoById(id);
			
			info.setRewardType(rewardType);
			info.setReason(reason);
			info.setRewardMoney(rewardMoney);
			
			this.empRewardInfoService.modifyEmpRewardInfo(info);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改员工奖惩信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeEmpRewardInfo")
	public void removeEmpRewardInfo() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.empRewardInfoService.removeEmpRewardInfo(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除员工奖惩信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}

}
