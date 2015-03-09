package org.eps.safetyinspection.vo;

import java.io.Serializable;

public class AccidentInfoVO implements Serializable {

	private static final long serialVersionUID = -247751127753942138L;

	private String accidentYear;
	private Long accidentNum;

	public String getAccidentYear() {
		return accidentYear;
	}

	public void setAccidentYear(String accidentYear) {
		this.accidentYear = accidentYear;
	}

	public Long getAccidentNum() {
		return accidentNum;
	}

	public void setAccidentNum(Long accidentNum) {
		this.accidentNum = accidentNum;
	}

}
