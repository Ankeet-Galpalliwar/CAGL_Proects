package com.cagl.dto.responce;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TwoWheelerResponsiveResponce {

	@JsonProperty("Loan_ID")
	private String Loan_ID;

	@JsonProperty("FINAL_DECISION")
	private String FINAL_DECISION;

	@JsonProperty("Request_date")
	private String Request_date;

	@JsonProperty("Derived_Attribute_4")
	private String Derived_Attribute_4;

	@JsonProperty("Customer_id")
	private String Customer_id;

	@JsonProperty("Derived_Attribute_2")
	private String Derived_Attribute_2;

	@JsonProperty("Derived_Attribute_1")
	private String Derived_Attribute_1;

	@JsonProperty("Derived_Attribute_3")
	private String Derived_Attribute_3;

	@JsonProperty("Segment")
	private String Segment;

}
