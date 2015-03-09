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

@Entity
@Table(name = "emp_reward_info")
public class EmpRewardInfo implements Serializable {

	private static final long serialVersionUID = -5859909711591602698L;

	private Long id;
	private Long empId;
	private Long rewardType;
	private String reason;
	private Date rewardTime;
	private Double rewardMoney;

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

	@Column(name = "reward_type")
	public Long getRewardType() {
		return rewardType;
	}

	public void setRewardType(Long rewardType) {
		this.rewardType = rewardType;
	}

	@Column(name = "reward_reason")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "reward_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}
	
	@Column(name = "reward_money")
	public Double getRewardMoney() {
		return rewardMoney;
	}
	
	public void setRewardMoney(Double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}

}
