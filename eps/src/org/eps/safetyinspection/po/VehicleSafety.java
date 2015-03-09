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
@Table(name = "vehicle_safety")
public class VehicleSafety implements Serializable {

	private static final long serialVersionUID = -3321828297068209723L;

	private Long id;
	private String inspectionItem;
	private String inspectionContent;
	private Long qualifiedFlag;
	private String correctiveAction;
	private String remark;
	private Date inspectionDate;
	
	private String inspectionDateStr;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "inspection_item")
	public String getInspectionItem() {
		return inspectionItem;
	}

	public void setInspectionItem(String inspectionItem) {
		this.inspectionItem = inspectionItem;
	}

	@Column(name = "inspection_content")
	public String getInspectionContent() {
		return inspectionContent;
	}

	public void setInspectionContent(String inspectionContent) {
		this.inspectionContent = inspectionContent;
	}

	@Column(name = "qualified_flag")
	public Long getQualifiedFlag() {
		return qualifiedFlag;
	}

	public void setQualifiedFlag(Long qualifiedFlag) {
		this.qualifiedFlag = qualifiedFlag;
	}

	@Column(name = "corrective_action")
	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "inspection_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getInspectionDate() {
		return inspectionDate;
	}
	
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	
	@Transient
	public String getInspectionDateStr() {
		return inspectionDateStr;
	}
	
	public void setInspectionDateStr(String inspectionDateStr) {
		this.inspectionDateStr = inspectionDateStr;
	}
	
}
