
package com.cagl.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class customer_eligible {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerID;
	private String gender;
	private String customerName;
	private java.sql.Date DOB;
	private long primaryMobileNumber;
	private long secondaryMobileNumber;
	private String occupation;
	private String KYCtype1;
	private String KYCnumber1;
	private String addressLine1;
	private String addressLine2;
	private long pincode;
	private String district;
	private String state;
	private String typeOfLoan;
	private String typeOfCustomer;
	private long loanAmount;
	private String remarks;
	private String kendra_id;
	private String kendraName;
	private String branch_id;
	private String status;
	@Column(name = "BM_KM")
	private int diff;
	
	

	public String getKendraName() {
		return kendraName;
	}

	public void setKendraName(String kendraName) {
		this.kendraName = kendraName;
	}

	public String getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}

	public String getKendra_id() {
		return kendra_id;
	}

	public void setKendra_id(String kendra_id) {
		this.kendra_id = kendra_id;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public java.sql.Date getDOB() {
		return DOB;
	}

	public void setDOB(java.sql.Date dOB) {
		DOB = dOB;
	}

	public long getPrimaryMobileNumber() {
		return primaryMobileNumber;
	}

	public void setPrimaryMobileNumber(long primaryMobileNumber) {
		this.primaryMobileNumber = primaryMobileNumber;
	}

	public long getSecondaryMobileNumber() {
		return secondaryMobileNumber;
	}

	public void setSecondaryMobileNumber(long secondaryMobileNumber) {
		this.secondaryMobileNumber = secondaryMobileNumber;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getKYCtype1() {
		return KYCtype1;
	}

	public void setKYCtype1(String kYCtype1) {
		KYCtype1 = kYCtype1;
	}

	public String getKYCnumber1() {
		return KYCnumber1;
	}

	public void setKYCnumber1(String kYCnumber1) {
		KYCnumber1 = kYCnumber1;
	}
	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public long getPincode() {
		return pincode;
	}

	public void setPincode(long pincode) {
		this.pincode = pincode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTypeOfLoan() {
		return typeOfLoan;
	}

	public void setTypeOfLoan(String typeOfLoan) {
		this.typeOfLoan = typeOfLoan;
	}

	public String getTypeOfCustomer() {
		return typeOfCustomer;
	}

	public void setTypeOfCustomer(String typeOfCustomer) {
		this.typeOfCustomer = typeOfCustomer;
	}

	public long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}
	

	public customer_eligible() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
