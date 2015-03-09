package org.eps.hrm.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "emp_skill")
public class EmpSkill implements Serializable {

	private static final long serialVersionUID = -3642568888983259100L;

	private Long id;
	private Long empId;
	private String skillName;
	private Long skillLevel;
	private String certNo;
	private String certUnit;
	private Date acquireTime;
	
	private String acquireTimeStr;
	private String skillLevelStr;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "emp_id")
	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	@Column(name = "skill_name")
	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Column(name = "skill_level")
	public Long getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(Long skillLevel) {
		this.skillLevel = skillLevel;
	}

	@Column(name = "cert_no")
	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	@Column(name = "cert_unit")
	public String getCertUnit() {
		return certUnit;
	}

	public void setCertUnit(String certUnit) {
		this.certUnit = certUnit;
	}

	@Column(name = "acquire_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAcquireTime() {
		return acquireTime;
	}

	public void setAcquireTime(Date acquireTime) {
		this.acquireTime = acquireTime;
	}
	
	@Transient
	public String getAcquireTimeStr() {
		return acquireTimeStr;
	}
	
	public void setAcquireTimeStr(String acquireTimeStr) {
		this.acquireTimeStr = acquireTimeStr;
	}
	
	@Transient
	public String getSkillLevelStr() {
		return skillLevelStr;
	}
	
	public void setSkillLevelStr(String skillLevelStr) {
		this.skillLevelStr = skillLevelStr;
	}

}
