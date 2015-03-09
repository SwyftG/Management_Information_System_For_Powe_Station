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
@Table(name = "accident_info")
public class AccidentInfo implements Serializable {

	private static final long serialVersionUID = 2950475504972145775L;

	private Long id;
	private String accidentTitle;
	private String accidentContent;
	private Date accidentTime;
	private String accidentSolution;
	
	private String accidentTimeStr;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "accident_title")
	public String getAccidentTitle() {
		return accidentTitle;
	}

	public void setAccidentTitle(String accidentTitle) {
		this.accidentTitle = accidentTitle;
	}

	@Column(name = "accident_content")
	public String getAccidentContent() {
		return accidentContent;
	}

	public void setAccidentContent(String accidentContent) {
		this.accidentContent = accidentContent;
	}

	@Column(name = "accident_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAccidentTime() {
		return accidentTime;
	}

	public void setAccidentTime(Date accidentTime) {
		this.accidentTime = accidentTime;
	}

	@Column(name = "accident_solution")
	public String getAccidentSolution() {
		return accidentSolution;
	}

	public void setAccidentSolution(String accidentSolution) {
		this.accidentSolution = accidentSolution;
	}
	
	@Transient
	public String getAccidentTimeStr() {
		return accidentTimeStr;
	}
	
	public void setAccidentTimeStr(String accidentTimeStr) {
		this.accidentTimeStr = accidentTimeStr;
	}

}
