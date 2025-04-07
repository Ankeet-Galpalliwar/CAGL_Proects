package com.cagl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewMenberDetail {
	
	private String kendraid;
	private String kendra;
	private String kendramanager;
	private String customer_id;
	private String Memeber_name;
	private String Customer_name;
	private String created_date;
	private String customer_activation_date;
	private String Bank_Name;
	private String Account_Holder_Name;
	private String Account_Number;
	private String IFSC_code;
	private String KYC_type;
	private String KYC_ID;

}
