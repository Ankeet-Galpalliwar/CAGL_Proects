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
public class AuditTrail {
	
	@Id
	private String memberID;
	private String uniqueNo;
	private String insuredCity;
	private String insuredState;
	private int  pinCode;
	private String mobileNo;
	private String policyNumber;
	private Date policyStartDate;
	private Date policyEndDate;
	private Date renewalDate;
	private String renewalFlag;
	private long premiumAmount;
	private String paymentMode;
	private String paymentReferenceNo;
	private Date paymentDate;
	private String insuredName;
	private Date insuredDOB;
	private String insuredGender;
	private String insuredRelationship;
	private String packageDetail;
	private String upiPaymentRecivedACNo;
	private String upiPaymentRecivedBankNameNo;
	private String memberBankName;
	private String memberBankAccountNumber;
	private String memberBankIfsc;
	private String branchID;
	private String branchName;
	private String kmID;
	private String kmName;
	private String kendraID;
	private String kendraName;
	private String bmID;
	private String bmName;
	private String areaName;
	private String regionAndDivision;
	private String sourcingState;
	private String firstDateOfKMEnrollment;
	private Date submittedbyKMDate;
	private Date approvedByBmDate;
	private Date voucherGenerationDate;
	private Date insuranceOpsCheckerActionDate;
	private String insuranceOpscheckerName;
	private Date sentToInsurerDate;
	private Date approvedbyInsurerDate;
	private Date coiuploaddate;
	private Date coissueddate;
	private Date rejectiondate;
	private String enrollmentStatus;
	private String insurerName;
	private String masterPolicyNumber;
	private Date enrollmentStatusDate;
	private String enrollmentRejectionReason;
	private String voucherNumber;
	private String premiumRefunded;
	private String premiumrefundedby;
	private Date premiumRefundDate;
	private String premiumrefundUTRN;
	private String comment ;
	private String premiumPaymentUTRNNumber;
	 //================
 	private String time_zone;
}
