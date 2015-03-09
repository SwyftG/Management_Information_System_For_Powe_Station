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
import org.eps.safetyinspection.po.SafetyTool;
import org.eps.safetyinspection.service.SafetyToolService;

public class SafetyToolAction extends BaseAction {

	private static final long serialVersionUID = -289074333016044215L;

	@Resource(name = "safetyToolService")
	private SafetyToolService safetyToolService;

	private Long id;
	private String toolName;
	private String toolDesc;
	private String toolImg;

	private String selectedIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolDesc() {
		return toolDesc;
	}

	public void setToolDesc(String toolDesc) {
		this.toolDesc = toolDesc;
	}

	public String getToolImg() {
		return toolImg;
	}

	public void setToolImg(String toolImg) {
		this.toolImg = toolImg;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	@Action("listAllSafetyTool")
	public void listAllSafetyTool() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.safetyToolService.findAllSafetyTool();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addSafetyTool")
	public void addSafetyTool() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			SafetyTool tool = new SafetyTool();
			
			tool.setToolName(toolName);
			tool.setToolDesc(toolDesc);
			
			if (StringUtils.isNotBlank(toolImg)) {
				tool.setToolImg(toolImg);
			} else {
				tool.setToolImg(StringUtils.EMPTY);
			}
			
			tool.setCreateTime(new Date());
			
			this.safetyToolService.addSafetyTool(tool);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "新增安全工具信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("loadSafetyTool")
	public void loadSafetyTool() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			SafetyTool tool = this.safetyToolService.findSafetyToolById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, tool);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载安全工具信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("modifySafetyTool")
	public void modifySafetyTool() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			SafetyTool tool = this.safetyToolService.findSafetyToolById(id);
			
			tool.setToolName(toolName);
			tool.setToolDesc(toolDesc);
			
			if (StringUtils.isNotBlank(toolImg)) {
				tool.setToolImg(toolImg);
			} else {
				tool.setToolImg(StringUtils.EMPTY);
			}
			
			tool.setCreateTime(new Date());
			
			this.safetyToolService.modifySafetyTool(tool);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改安全工具信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeSafetyTool")
	public void removeSafetyTool() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.safetyToolService.removeSafetyTool(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除安全工具信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}

}
