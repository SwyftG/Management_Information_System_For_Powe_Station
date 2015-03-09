package org.eps.hrm.vo;

import java.io.Serializable;

public class EmpSkillVO implements Serializable {

	private static final long serialVersionUID = -377918994470658557L;

	private String personCode;
	private String personName;
	private String orgName;
	private String skillName;
	private String skillLevel;
	private String certNo;
	private String certUnit;
	private String acquireTime;

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertUnit() {
		return certUnit;
	}

	public void setCertUnit(String certUnit) {
		this.certUnit = certUnit;
	}

	public String getAcquireTime() {
		return acquireTime;
	}

	public void setAcquireTime(String acquireTime) {
		this.acquireTime = acquireTime;
	}

}
