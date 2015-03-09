package org.eps.device.po;

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
@Table(name = "deviceinfo")
public class DeviceInfo implements Serializable {

	private static final long serialVersionUID = 4571966416257399361L;

	private Long id;
	private String deviceName;
	private String deviceNo;
	private Double deviceMoney;
	private Long deviceType;
	private String deviceDesc;
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

	@Column(name = "device_name")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "device_no")
	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	@Column(name = "device_money")
	public Double getDeviceMoney() {
		return deviceMoney;
	}

	public void setDeviceMoney(Double deviceMoney) {
		this.deviceMoney = deviceMoney;
	}

	@Column(name = "device_type")
	public Long getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}
	
	@Column(name = "device_desc")
	public String getDeviceDesc() {
		return deviceDesc;
	}
	
	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
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
