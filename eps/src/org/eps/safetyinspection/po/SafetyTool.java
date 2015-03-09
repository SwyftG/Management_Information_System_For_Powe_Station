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
@Table(name = "safety_tool")
public class SafetyTool implements Serializable {

	private static final long serialVersionUID = -2652911434655282764L;

	private Long id;
	private String toolName;
	private String toolDesc;
	private String toolImg;
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

	@Column(name = "tool_name")
	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	@Column(name = "tool_desc")
	public String getToolDesc() {
		return toolDesc;
	}

	public void setToolDesc(String toolDesc) {
		this.toolDesc = toolDesc;
	}

	@Column(name = "tool_img")
	public String getToolImg() {
		return toolImg;
	}

	public void setToolImg(String toolImg) {
		this.toolImg = toolImg;
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
