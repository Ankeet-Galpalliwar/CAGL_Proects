package com.cagl.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "dbo.DigiAgil_Customer_FOIR_data_Two_wheeler")
public class DigiAgilCustomer {

	@Id
	private String customer_id;
	private String loan_id;
	private int request_id;
	private float Familyincome;
	private float applied_loan_emi;
	private float eligible_emi;
	private int CAGL_Vintage;
	private String first_loan_disb_date;
	private int CAGL_portfolio;
	private Date request_date;
	private String Applied_loan_amount;
	private int CAGL_CUST_OVERDUE;
	private int CAGL_CUST_DPD;
	private int NO_Of_ENQUIRIES;
	private int PRODUCT_ID;
	private String Branch_ID;

}
