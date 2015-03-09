package org.eps.hrquery.action;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.eps.common.action.BaseAction;
import org.eps.common.util.Constants;
import org.eps.common.util.EncodingUtil;
import org.eps.common.util.Tools;
import org.eps.common.vo.JsonVO;
import org.eps.hrquery.po.OfficeDocument;
import org.eps.hrquery.service.OfficeDocumentService;

public class OfficeDocumentAction extends BaseAction {

	private static final long serialVersionUID = -6838628625791426715L;

	@Resource(name = "officeDocumentService")
	private OfficeDocumentService officeDocumentService;

	private String beginDate;
	private String endDate;

	private Long id;
	private Long orgId;
	private String title;
	private String fileNo;
	private String filePath;
	
	private String selectedIds;
	
	private InputStream input;
	
	private String fileName;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getSelectedIds() {
		return selectedIds;
	}
	
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	public InputStream getInput() {
		return input;
	}
	
	public void setInput(InputStream input) {
		this.input = input;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Action("listDocumentByCondition")
	public void listDocumentByCondition() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			PrintWriter out = response.getWriter();
			
			Map<String, Object> condition = buildCondition();

			String json = this.officeDocumentService.findDocumentByCondition(condition);

			out.write(json);

			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("addDocument")
	public void addDocument() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			OfficeDocument document = new OfficeDocument();
			
			document.setOrgId(orgId);
			document.setTitle(title);
			document.setFileNo(RandomStringUtils.random(6, true, true));
			
			if (StringUtils.isNotBlank(filePath)) {
				document.setFilePath(filePath);
			} else {
				document.setFilePath(StringUtils.EMPTY);
			}
			
			document.setPublishDate(new Date());
			
			this.officeDocumentService.addOfficeDocument(document);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "添加劳资文件时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeDocument")
	public void removeDocument() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);

			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.officeDocumentService.removeOfficeDocument(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除劳资文件时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action(value = "downloadDocument", results = {
		@Result(name = "success", type = "stream", params = {
			"contentType", "application/octet-stream",
			"inputName", "input",
			"contentDisposition", "attachment;filename=${fileName}",
			"bufferSize", "1024"
		})
	})
	public String downloadDocument() throws Exception {
		String param = request.getParameter("filePath");
		
		param = URLDecoder.decode(param, "UTF-8");
		
		String name = param.substring(param.indexOf("_") + 1);
		
		this.fileName = EncodingUtil.encodingString(name);
		
		ServletContext sc = ServletActionContext.getServletContext();
		
		String path = sc.getRealPath(Constants.DOCUMENT_UPLOAD_FILE_PATH) + File.separator + param.substring(param.lastIndexOf("/") + 1);
		
		this.input = FileUtils.openInputStream(new File(path));
		
		return SUCCESS;
	}
	
	private Map<String, Object> buildCondition() {
		Map<String, Object> condition = new HashMap<String, Object>();
		
		condition.put("beginDate", beginDate);
		condition.put("endDate", endDate);
		condition.put("orgId", orgId);
		condition.put("title", title);
		condition.put("fileNo", fileNo);
		
		return condition;
	}

}
