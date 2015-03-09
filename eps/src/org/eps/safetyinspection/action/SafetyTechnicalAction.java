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
import org.eps.safetyinspection.po.SafetyTechnical;
import org.eps.safetyinspection.service.SafetyTechnicalService;

public class SafetyTechnicalAction extends BaseAction {

	private static final long serialVersionUID = -7449110509948854187L;

	@Resource(name = "safetyTechnicalService")
	private SafetyTechnicalService safetyTechnicalService;

	private Long id;
	private String safetyContent;
	private String technicalContent;

	private String selectedIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSafetyContent() {
		return safetyContent;
	}

	public void setSafetyContent(String safetyContent) {
		this.safetyContent = safetyContent;
	}

	public String getTechnicalContent() {
		return technicalContent;
	}

	public void setTechnicalContent(String technicalContent) {
		this.technicalContent = technicalContent;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	@Action("listAllSafetyTechnical")
	public void listAllSafetyTechnical() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			PrintWriter out = response.getWriter();

			String json = this.safetyTechnicalService.findAllSafetyTechnical();

			out.write(json);

			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addSafetyTechnical")
	public void addSafetyTechnical() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			SafetyTechnical st = new SafetyTechnical();
			
			st.setSafetyContent(safetyContent);
			st.setTechnicalContent(technicalContent);
			st.setCreateTime(new Date());
			
			this.safetyTechnicalService.addSafetyTechnical(st);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "添加安反措信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("loadSafetyTechnical")
	public void loadSafetyTechnical() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			SafetyTechnical st = this.safetyTechnicalService.findSafetyTechnicalById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, st);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载安反措信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);

		IOUtils.closeQuietly(out);
	}

	@Action("modifySafetyTechnical")
	public void modifySafetyTechnical() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			SafetyTechnical st = this.safetyTechnicalService.findSafetyTechnicalById(id);
			
			st.setSafetyContent(safetyContent);
			st.setTechnicalContent(technicalContent);
			
			this.safetyTechnicalService.modifySafetyTechnical(st);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改安反措信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
	@Action("removeSafetyTechnical")
	public void removeSafetyTechnical() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.safetyTechnicalService.removeSafetyTechnical(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除安反措信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);

		IOUtils.closeQuietly(out);
	}
	
}
