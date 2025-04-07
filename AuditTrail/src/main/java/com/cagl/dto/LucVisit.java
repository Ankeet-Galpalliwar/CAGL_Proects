package com.cagl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LucVisit {
	
	private String kendraid;
	private String kendra;
	private String kendramanager;
	private String group_id;
	private String group_name;
	private String entity_id;
	private String Memeber_name;
	private String customer_activation_date;
	private String global_account_num;
	private String loan_amount;
	private String disbursement_date;
	private String account_id;
	private String product;
	private String Purpose;
	private String branch_id;
	private String Principal_outstanding;
	private String Overdue;
	private String customer_id;

}
