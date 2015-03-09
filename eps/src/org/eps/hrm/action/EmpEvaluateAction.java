package org.eps.hrm.action;

import java.io.PrintWriter;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.eps.common.action.BaseAction;
import org.eps.common.util.Constants;
import org.eps.common.util.Tools;
import org.eps.common.vo.JsonVO;
import org.eps.hrm.po.EmpEvaluate;
import org.eps.hrm.service.EmpEvaluateService;

public class EmpEvaluateAction extends BaseAction {

	private static final long serialVersionUID = -5290669099951971293L;

	@Resource(name = "empEvaluateService")
	private EmpEvaluateService empEvaluateService;

	private Long empId;
	private Long evaluateLevel;
	private Integer evaluateYear;
	
	private String selectedIds;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getEvaluateLevel() {
		return evaluateLevel;
	}

	public void setEvaluateLevel(Long evaluateLevel) {
		this.evaluateLevel = evaluateLevel;
	}

	public Integer getEvaluateYear() {
		return evaluateYear;
	}

	public void setEvaluateYear(Integer evaluateYear) {
		this.evaluateYear = evaluateYear;
	}
	
	public String getSelectedIds() {
		return selectedIds;
	}
	
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	@Action("listAllEmpEvaluate")
	public void listAllEmpEvaluate() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			PrintWriter out = response.getWriter();

			String json = this.empEvaluateService.findAllEmpEvaluate();

			out.write(json);

			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addEmpEvaluate")
	public void addEmpEvaluate() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			EmpEvaluate evaluate = new EmpEvaluate();
			
			evaluate.setEmpId(empId);
			evaluate.setEvaluateLevel(evaluateLevel);
			evaluate.setEvaluateYear(evaluateYear);
			
			this.empEvaluateService.addEmpEvaluate(evaluate);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, e.getMessage());
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeEmpEvaluate")
	public void removeEmpEvaluate() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.empEvaluateService.removeEmpEvaluate(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, e.getMessage());
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("generateEmpEvaluateChart")
	public void generateEmpEvaluateChart() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			String xml = this.empEvaluateService.generateEmpEvaluateChartXml(evaluateYear);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, StringUtils.EMPTY, xml);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "生成员工评价图表XML时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}

}
