package org.eps.hrm.vo;

import java.io.Serializable;

public class EmpEvaluateVO implements Serializable {

	private static final long serialVersionUID = 6470440054169968704L;

	private String evaluateLevel;
	private Long num;

	public String getEvaluateLevel() {
		return evaluateLevel;
	}
	
	public void setEvaluateLevel(String evaluateLevel) {
		this.evaluateLevel = evaluateLevel;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

}
