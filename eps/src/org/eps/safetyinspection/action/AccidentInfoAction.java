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
import org.eps.safetyinspection.po.AccidentInfo;
import org.eps.safetyinspection.service.AccidentInfoService;

public class AccidentInfoAction extends BaseAction {

	private static final long serialVersionUID = 1890491630577969408L;

	@Resource(name = "accidentInfoService")
	private AccidentInfoService accidentInfoService;

	private Long id;
	private String accidentTitle;
	private String accidentContent;
	private Date accidentTime;
	private String accidentSolution;

	private String selectedIds;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAccidentTitle() {
		return accidentTitle;
	}

	public void setAccidentTitle(String accidentTitle) {
		this.accidentTitle = accidentTitle;
	}

	public String getAccidentContent() {
		return accidentContent;
	}

	public void setAccidentContent(String accidentContent) {
		this.accidentContent = accidentContent;
	}

	public String getAccidentSolution() {
		return accidentSolution;
	}

	public void setAccidentSolution(String accidentSolution) {
		this.accidentSolution = accidentSolution;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	public Date getAccidentTime() {
		return accidentTime;
	}
	
	public void setAccidentTime(Date accidentTime) {
		this.accidentTime = accidentTime;
	}

	@Action("listAllAccidentInfo")
	public void listAllAccidentInfo() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.accidentInfoService.findAllAccidentInfo();
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addAccidentInfo")
	public void addAccidentInfo() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			AccidentInfo info = new AccidentInfo();
			
			info.setAccidentTitle(accidentTitle);
			info.setAccidentContent(accidentContent);
			info.setAccidentSolution(accidentSolution);
			info.setAccidentTime(accidentTime);
			
			this.accidentInfoService.addAccidentInfo(info);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "新增事故信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("loadAccidentInfo")
	public void loadAccidentInfo() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			AccidentInfo info = this.accidentInfoService.findAccidentInfoById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, info);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载事故信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("modifyAccidentInfo")
	public void modifyAccidentInfo() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			AccidentInfo info = this.accidentInfoService.findAccidentInfoById(id);
			
			info.setAccidentTitle(accidentTitle);
			info.setAccidentContent(accidentContent);
			info.setAccidentSolution(accidentSolution);
			info.setAccidentTime(accidentTime);
			
			this.accidentInfoService.modifyAccidentInfo(info);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, info);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改事故信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeAccidentInfo")
	public void removeAccidentInfo() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.accidentInfoService.removeAccidentInfo(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除事故信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("buildAccidentInfoChart")
	public void buildAccidentInfoChart() {
		PrintWriter out = null;
		JsonVO vo = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			String xml = this.accidentInfoService.generateChartXml();
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, StringUtils.EMPTY, xml);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载XML数据发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}

}
