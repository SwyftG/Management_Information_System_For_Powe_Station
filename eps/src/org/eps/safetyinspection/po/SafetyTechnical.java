package org.eps.safetyinspection.po;

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
@Table(name = "safety_technical")
public class SafetyTechnical implements Serializable {

	private static final long serialVersionUID = -1868573868450796510L;

	private Long id;
	private String safetyContent;
	private String technicalContent;
	private Date createTime;

	private String createTimeStr;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "safety_content")
	public String getSafetyContent() {
		return safetyContent;
	}

	public void setSafetyContent(String safetyContent) {
		this.safetyContent = safetyContent;
	}

	@Column(name = "technical_content")
	public String getTechnicalContent() {
		return technicalContent;
	}

	public void setTechnicalContent(String technicalContent) {
		this.technicalContent = technicalContent;
	}

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Transient
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

}
