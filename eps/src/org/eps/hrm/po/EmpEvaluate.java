package org.eps.hrm.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.persistence.Entity;

@Entity
@Table(name = "emp_evaluate")
public class EmpEvaluate implements Serializable {

	private static final long serialVersionUID = 1053297406360072669L;

	private Long id;
	private Long empId;
	private Long evaluateLevel;
	private Integer evaluateYear;

	private String empName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "evaluate_emp_id")
	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	@Column(name = "evaluate_level")
	public Long getEvaluateLevel() {
		return evaluateLevel;
	}

	public void setEvaluateLevel(Long evaluateLevel) {
		this.evaluateLevel = evaluateLevel;
	}

	@Column(name = "evaluate_year")
	public Integer getEvaluateYear() {
		return evaluateYear;
	}

	public void setEvaluateYear(Integer evaluateYear) {
		this.evaluateYear = evaluateYear;
	}

	@Transient
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
