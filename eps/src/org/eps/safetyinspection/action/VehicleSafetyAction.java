package org.eps.safetyinspection.action;

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
import org.eps.safetyinspection.po.VehicleSafety;
import org.eps.safetyinspection.service.VehicleSafetyService;

public class VehicleSafetyAction extends BaseAction {

	private static final long serialVersionUID = 7222915684822428362L;
	
	@Resource(name = "vehicleSafetyService")
	private VehicleSafetyService vehicleSafetyService;
	
	private Long id;
	private String inspectionItem;
	private String inspectionContent;
	private Long qualifiedFlag;
	private String correctiveAction;
	private String remark;
	
	private String selectedIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInspectionItem() {
		return inspectionItem;
	}

	public void setInspectionItem(String inspectionItem) {
		this.inspectionItem = inspectionItem;
	}

	public String getInspectionContent() {
		return inspectionContent;
	}

	public void setInspectionContent(String inspectionContent) {
		this.inspectionContent = inspectionContent;
	}

	public Long getQualifiedFlag() {
		return qualifiedFlag;
	}

	public void setQualifiedFlag(Long qualifiedFlag) {
		this.qualifiedFlag = qualifiedFlag;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	@Action("listAllVehicleSafety")
	public void listAllVehicleSafety() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.vehicleSafetyService.findAllVehicleSafety();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Action("addVehicleSafety")
	public void addVehicleSafety() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			VehicleSafety safety = new VehicleSafety();
			
			safety.setInspectionItem(inspectionItem);
			safety.setInspectionContent(inspectionContent);
			safety.setQualifiedFlag(qualifiedFlag);
			safety.setCorrectiveAction(correctiveAction);
			safety.setRemark(remark);
			safety.setInspectionDate(new Date());
			
			this.vehicleSafetyService.addVehicleSafety(safety);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "新增车辆安全检查详情时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("loadVehicleSafety")
	public void loadVehicleSafety() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			VehicleSafety safety = this.vehicleSafetyService.findVehicleSafetyById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, safety);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载车辆安全检查详情时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("modifyVehicleSafety")
	public void modifyVehicleSafety() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			VehicleSafety safety = this.vehicleSafetyService.findVehicleSafetyById(id);
			
			safety.setInspectionItem(inspectionItem);
			safety.setInspectionContent(inspectionContent);
			safety.setQualifiedFlag(qualifiedFlag);
			safety.setCorrectiveAction(correctiveAction);
			safety.setRemark(remark);
			
			this.vehicleSafetyService.modifyVehicleSafety(safety);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改车辆安全检查详情时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeVehicleSafety")
	public void removeVehicleSafety() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.vehicleSafetyService.removeVehicleSafety(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除车辆安全检查详情时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
}
