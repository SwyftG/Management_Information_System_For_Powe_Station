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
@Table(name = "emp_exchange_info")
public class EmpExchangeInfo implements Serializable {

	private static final long serialVersionUID = -2035067513596350056L;

	private Long id;
	private Long empId;
	private Long oldOrgId;
	private Long newOrgId;
	private String oldPost;
	private String newPost;
	private Double oldSalary;
	private Double newSalary;
	private String exchangeNo;
	private String exchangeReason;
	private Long postChangeFlag;
	private Date exchangeDate;

	private String empName;
	private String oldOrg;
	private String newOrg;
	private String exchangeDateStr;

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

	@Column(name = "old_orgid")
	public Long getOldOrgId() {
		return oldOrgId;
	}

	public void setOldOrgId(Long oldOrgId) {
		this.oldOrgId = oldOrgId;
	}

	@Column(name = "new_orgid")
	public Long getNewOrgId() {
		return newOrgId;
	}

	public void setNewOrgId(Long newOrgId) {
		this.newOrgId = newOrgId;
	}

	@Column(name = "old_post")
	public String getOldPost() {
		return oldPost;
	}

	public void setOldPost(String oldPost) {
		this.oldPost = oldPost;
	}

	@Column(name = "new_post")
	public String getNewPost() {
		return newPost;
	}

	public void setNewPost(String newPost) {
		this.newPost = newPost;
	}

	@Column(name = "old_salary")
	public Double getOldSalary() {
		return oldSalary;
	}

	public void setOldSalary(Double oldSalary) {
		this.oldSalary = oldSalary;
	}

	@Column(name = "new_salary")
	public Double getNewSalary() {
		return newSalary;
	}

	public void setNewSalary(Double newSalary) {
		this.newSalary = newSalary;
	}

	@Column(name = "exchange_no")
	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	@Column(name = "exchange_reason")
	public String getExchangeReason() {
		return exchangeReason;
	}

	public void setExchangeReason(String exchangeReason) {
		this.exchangeReason = exchangeReason;
	}
	
	@Column(name = "post_change_flag")
	public Long getPostChangeFlag() {
		return postChangeFlag;
	}
	
	public void setPostChangeFlag(Long postChangeFlag) {
		this.postChangeFlag = postChangeFlag;
	}

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

	@Transient
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Transient
	public String getOldOrg() {
		return oldOrg;
	}

	public void setOldOrg(String oldOrg) {
		this.oldOrg = oldOrg;
	}

	@Transient
	public String getNewOrg() {
		return newOrg;
	}

	public void setNewOrg(String newOrg) {
		this.newOrg = newOrg;
	}

	@Transient
	public String getExchangeDateStr() {
		return exchangeDateStr;
	}

	public void setExchangeDateStr(String exchangeDateStr) {
		this.exchangeDateStr = exchangeDateStr;
	}

}
