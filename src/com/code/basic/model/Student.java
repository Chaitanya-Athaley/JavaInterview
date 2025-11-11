package com.code.basic.model;

import java.util.Objects;

public class Student {

	private Integer rollnumber;
	private Integer totalNumber;
	private String branch;
	private String name;
	private String className;
	
	public Integer getRollnumber() {
		return rollnumber;
	}
	public void setRollnumber(Integer rollnumber) {
		this.rollnumber = rollnumber;
	}
	public Integer getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Override
	public int hashCode() {
		return Objects.hash(branch, className, name, rollnumber, totalNumber);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(branch, other.branch) && Objects.equals(className, other.className)
				&& Objects.equals(name, other.name) && Objects.equals(rollnumber, other.rollnumber)
				&& Objects.equals(totalNumber, other.totalNumber);
	}
	
	
	
}
