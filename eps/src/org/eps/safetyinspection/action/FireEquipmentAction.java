package org.eps.safetyinspection.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.eps.common.action.BaseAction;
import org.eps.common.util.Constants;
import org.eps.common.util.Tools;
import org.eps.common.vo.JsonVO;
import org.eps.safetyinspection.po.FireEquipment;
import org.eps.safetyinspection.service.FireEquipmentService;

public class FireEquipmentAction extends BaseAction {

	private static final long serialVersionUID = -3050375666204579825L;

	@Resource(name = "fireEquipmentService")
	private FireEquipmentService fireEquipmentService;

	private Long id;
	private String equipmentName;
	private String equipmentDesc;
	private String equipmentImg;

	private String selectedIds;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getEquipmentDesc() {
		return equipmentDesc;
	}

	public void setEquipmentDesc(String equipmentDesc) {
		this.equipmentDesc = equipmentDesc;
	}

	public String getEquipmentImg() {
		return equipmentImg;
	}

	public void setEquipmentImg(String equipmentImg) {
		this.equipmentImg = equipmentImg;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	@Action("listAllfireEquipment")
	public void listAllfireEquipment() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.fireEquipmentService.findAllEquipment();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addFireEquipment")
	public void addFireEquipment() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			FireEquipment equipment = new FireEquipment();
			
			equipment.setEquipmentName(equipmentName);
			equipment.setEquipmentDesc(equipmentDesc);
			
			if (StringUtils.isNotBlank(equipmentImg)) {
				equipment.setEquipmentImg(equipmentImg);
			} else {
				equipment.setEquipmentImg(StringUtils.EMPTY);
			}
			
			equipment.setCreateTime(new Date());
			
			this.fireEquipmentService.addFireEquipment(equipment);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "新增消防器材信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("loadFireEquipment")
	public void loadFireEquipment() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			FireEquipment equipment = this.fireEquipmentService.findFireEquipmentById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, equipment);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载消防器材信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("modifyFireEquipment")
	public void modifyFireEquipment() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			FireEquipment equipment = this.fireEquipmentService.findFireEquipmentById(id);
			
			equipment.setEquipmentName(equipmentName);
			equipment.setEquipmentDesc(equipmentDesc);
			
			if (StringUtils.isNotBlank(equipmentImg)) {
				equipment.setEquipmentImg(equipmentImg);
			} else {
				equipment.setEquipmentImg(StringUtils.EMPTY);
			}
			
			this.fireEquipmentService.modifyFireEquipment(equipment);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改消防器材信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeFireEquipment")
	public void removeFireEquipment() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.fireEquipmentService.removeFireEquipment(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除消防器材信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}

}
