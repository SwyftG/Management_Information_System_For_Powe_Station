package org.eps.hrm.vo;

import java.io.Serializable;

public class EmpRewardVO implements Serializable {

	private static final long serialVersionUID = -7747325581336414521L;

	private String id;
	private String empName;
	private String orgName;
	private String post;
	private String rewardType;
	private String rewardReason;
	private String rewardTime;
	private Double rewardMoney;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public String getRewardReason() {
		return rewardReason;
	}

	public void setRewardReason(String rewardReason) {
		this.rewardReason = rewardReason;
	}

	public String getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(String rewardTime) {
		this.rewardTime = rewardTime;
	}
	
	public Double getRewardMoney() {
		return rewardMoney;
	}
	
	public void setRewardMoney(Double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	
	public String getPost() {
		return post;
	}
	
	public void setPost(String post) {
		this.post = post;
	}

}
