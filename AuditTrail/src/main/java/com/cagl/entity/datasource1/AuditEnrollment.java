package com.cagl.entity.datasource1;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditEnrollment {
	
	
	
	@Id
	private String caglClaimNumber;
	private String memberID;
	private String uniqueNo;
	private String patientDOB;
	private String patientName;
	private String memberName;
	private long membercontactNumber;
	private String insuredPerson;
	private String packageDetail;
	private String gender;
	private Date policyStartDate;
	private Date policyEndDate;
	private Date admissionDate;
	private Date dischargeDate;
	private int hospitalizedDays;
	private int numberOfIcuAdmission;
	private int numberOfNonIcuAdmission;
	private long claimAmountApplied;
	private String insurerName;
	private String  masterPolicyNumber;
	private String policyNumber;
	private String bankAccountNumber;
	private String bankIfsc;
	private String sourcingState;
	private String branchID;
	private String branchName;
	private String kendraID;
	private String kendraName;
	private String kmID;
	private String kmName;
	private String kmContactNumber;
	private String bmID; 
	private String bmName;
	private String areaName;
	private String regionOrDivision;
	private Date firstDateOfClaimIntimation;
	private Date approvedByInsurerOpsMakerDate;
	private String approvedByInsurerOpsMakerName;
	private String approvedByInsurerOpsMakerID;
	private Date approvedByInsurerOpsCheckerDate;
	private String approvedByInsurerOpsCheckerName;
	private String approvedByInsurerOpsCheckerID;
	private Date sentToInsurerDate;
	private String claimStatus;
	private Date dateClaimSettlementDate;
	private long claimSettlementAmount;
	private String utrNumber;
	private Date utrDate;
	private Date statusDate;
	private String claimPushbackReason;
	private String claimRejectionReason;
	private String commentPushbackRejection;
	private Date rejectionDatePushbackToKMDate;
	private String pushedBackBy;
	private String pushedbackbyName;
	private String pushedbackbyID;
	private String pushbackCase;
	private String productCode;
	private String productName;
	private String claimedMemberType;
     private String policySumInsured;	
     private String leftSumAssured;
     private Date claimRegistrationDate;
     private long amountSettled;
     private String insurerClaimNumber;
   //================
 	private String time_zone;
	

	
	

}
