package org.eps.device.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.eps.common.action.BaseAction;
import org.eps.common.util.Constants;
import org.eps.common.util.Tools;
import org.eps.common.vo.JsonVO;
import org.eps.device.po.DeviceInfo;
import org.eps.device.service.DeviceInfoService;

public class DeviceInfoAction extends BaseAction {

	private static final long serialVersionUID = 932804799112788366L;
	
	@Resource(name = "deviceInfoService")
	private DeviceInfoService deviceInfoService;
	
	private Long id;
	private String deviceName;
	private String deviceNo;
	private Double deviceMoney;
	private Long deviceType;
	private String deviceDesc;
	
	private String selectedIds;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Double getDeviceMoney() {
		return deviceMoney;
	}

	public void setDeviceMoney(Double deviceMoney) {
		this.deviceMoney = deviceMoney;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceType(Long deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getDeviceDesc() {
		return deviceDesc;
	}
	
	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
	}
	
	public String getSelectedIds() {
		return selectedIds;
	}
	
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	@Action("listDevices")
	public void listDevices() {
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			PrintWriter out = response.getWriter();
			
			String json = this.deviceInfoService.findDevicesByType(deviceType);
			
			out.write(json);
			
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("createDevice")
	public void createDevice() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			DeviceInfo device = new DeviceInfo();
			
			device.setDeviceName(deviceName);
			device.setDeviceNo(deviceNo);
			device.setDeviceMoney(deviceMoney);
			device.setDeviceType(deviceType);
			device.setDeviceDesc(deviceDesc);
			device.setCreateTime(new Date());
			
			this.deviceInfoService.addDeviceInfo(device);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "添加设备信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("loadDeviceInfo")
	public void loadDeviceInfo() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			DeviceInfo device = this.deviceInfoService.findDeviceInfoById(id);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, device);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "加载设备信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("modifyDeivice")
	public void modifyDeivice() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			DeviceInfo device = this.deviceInfoService.findDeviceInfoById(id);
			
			device.setDeviceName(deviceName);
			device.setDeviceNo(deviceNo);
			device.setDeviceMoney(deviceMoney);
			device.setDeviceDesc(deviceDesc);
			
			this.deviceInfoService.modifyDeviceInfo(device);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS, device);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "修改设备信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}
	
	@Action("removeDevice")
	public void removeDevice() {
		JsonVO vo = null;
		PrintWriter out = null;
		
		try {
			response.setContentType(Constants.JSON_CONTENT_TYPE);
			response.setCharacterEncoding(Constants.DEFAULT_CHARSET);
			
			out = response.getWriter();
			
			Long[] ids = Tools.split2Array(selectedIds, Constants.DEFAULT_DELIMITER);
			
			this.deviceInfoService.removeDeviceInfo(ids);
			
			vo = new JsonVO(Constants.STATUS_SUCCESS);
		} catch (Exception e) {
			vo = new JsonVO(Constants.STATUS_ERROR, "删除设备信息时发生错误！");
		}
		
		String json = JSONObject.fromObject(vo).toString();
		
		out.write(json);
		
		IOUtils.closeQuietly(out);
	}

}
