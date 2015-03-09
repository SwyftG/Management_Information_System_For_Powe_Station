package org.eps.hrm.vo;

import java.io.Serializable;

public class EmpInfoTreeVO implements Serializable {

	private static final long serialVersionUID = -7785246714142897337L;
	
	private Long id;
	private String name;
	private Long pid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}
