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
import org.eps.hrm.po.EmpExchangeInfo;
import org.eps.hrm.service.EmpExchangeInfoService;

public class EmpExchangeInfoAction extends BaseAction {

	private static final long serialVersionUID = 2304412834848702339L;

	@Resource(name = "empExchangeInfoService")
	private EmpExchangeInfoService empExchangeInfoService;

	private Long id;
	private Long empId;
	private Long oldOrgId;
	private Long newOrgId;
	private String oldPost;
	private String newPost;
	private Double oldSalary;
	private Double newSalary;
	private String exchangeNo;
	private String exchangeReason;
	
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

	public Long getOldOrgId() {
		return oldOrgId;
	}

	public void setOldOrgId(Long oldOrgId) {
		this.oldOrgId = oldOrgId;
	}

	public Long getNewOrgId() {
		return newOrgId;
	}

	public void setNewOrgId(Long newOrgId) {
		this.newOrgId = newOrgId;
	}

	public String getOldPost() {
		return oldPost;
	}

	public void setOldPost(String oldPost) {
		this.oldPost = oldPost;
	}

	public String getNewPost() {
		return newPost;
	}

	public void setNewPost(String newPost) {
		this.newPost = newPost;
	}

	public Double getOldSalary() {
		return oldSalary;
	}

	public void setOldSalary(Double oldSalary) {
		this.oldSalary = oldSalary;
	}

	public Double getNewSalary() {
		return newSalary;
	}

	public void setNewSalary(Double newSalary) {
		this.newSalary = newSalary;
	}

	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	public String getExchangeReason() {
		return exchangeReason;
	}

	public void setExchangeReason(String exchangeReason) {
		this.exchangeReason = exchangeReason;
	}
	
	public String getSelectedIds() {
		return selectedIds;
	}
	
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	@Action("listAllEmpExchangeInfo")
	public void listAllEmpExchangeInfo() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.empExchangeInfoService.findAllEmpExchangeInfo();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("listAllPostChangeInfo")
	public void listAllPostChangeInfo() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.empExchangeInfoService.findAllPostChangeInfo();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addEmpExchangeInfo")
	public void addEmpExchangeInfo() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			EmpExchangeInfo info = new EmpExchangeInfo();
			
			info.setEmpId(empId);
			info.setOldOrgId(oldOrgId);
			info.setNewOrgId(newOrgId);
			info.setOldPost(oldPost);
			info.setNewPost(newPost);
			info.setOldSalary(oldSalary);
			info.setNewSalary(newSalary);
			info.setExchangeNo(exchangeNo);
			info.setExchangeReason(exchangeReason);
			
			if (oldPost.equals(newPost)) {
				info.setPostChangeFlag(Constants.POST_NO_CHANGE);
			} else {
				info.setPostChangeFlag(Constants.POST_CHANGE);
			}
			
			info.setExchangeDate(new Date());
			
			this.empExchangeInfoService.addEmpExchangeInfo(info);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "新增员工调动信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeEmpExchangeInfo")
	public void removeEmpExchangeInfo() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.empExchangeInfoService.removeEmpExchangeInfo(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除员工调动信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}

}
