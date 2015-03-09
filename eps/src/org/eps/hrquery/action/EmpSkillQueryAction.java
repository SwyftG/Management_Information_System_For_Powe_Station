package org.eps.hrquery.action;

import java.io.PrintWriter;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.eps.common.action.BaseAction;
import org.eps.common.util.Constants;
import org.eps.hrm.service.EmpSkillService;

public class EmpSkillQueryAction extends BaseAction {

	private static final long serialVersionUID = 4593159111693207747L;

	@Resource(name = "empSkillService")
	private EmpSkillService empSkillService;

	private String personCode;
	private String name;
	private Long orgId;

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	@Action("listEmpSkillByCondition")
	public void listEmpSkillByCondition() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.empSkillService.findEmpSkillByCondition(personCode, name, orgId);
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
