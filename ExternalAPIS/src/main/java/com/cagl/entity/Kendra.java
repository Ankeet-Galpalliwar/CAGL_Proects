package com.cagl.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Kendra 
{
	@Id
	private String kendraId;
	private String kendraName;
	private String branch_id;
	private String employee_id;
	
	

	@Override
	public String toString() {
		return "Kendra [kendraId=" + kendraId + ", kendraName=" + kendraName + ", branch_id=" + branch_id
				+ ", employee_id=" + employee_id + "]";
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public Kendra() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getKendraId() {
		return kendraId;
	}

	public void setKendraId(String kendraId) {
		this.kendraId = kendraId;
	}

	public String getKendraName() {
		return kendraName;
	}

	public void setKendraName(String kendraName) {
		this.kendraName = kendraName;
	}

}
