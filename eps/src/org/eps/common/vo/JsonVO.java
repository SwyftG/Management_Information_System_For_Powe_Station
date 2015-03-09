package org.eps.common.vo;

import java.io.Serializable;

public class JsonVO implements Serializable {

	private static final long serialVersionUID = 7795631796890575953L;

	private String status;

	private String message;

	private Object data;

	public JsonVO() {

	}
	
	public JsonVO(String status) {
		this.status = status;
	}

	public JsonVO(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public JsonVO(String status, Object data) {
		this.status = status;
		this.data = data;
	}

	public JsonVO(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
