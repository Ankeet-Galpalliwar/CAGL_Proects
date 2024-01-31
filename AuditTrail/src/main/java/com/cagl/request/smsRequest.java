package com.cagl.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class smsRequest {

	@JsonProperty("transId")
	private String transId;
	@JsonProperty("msisdn")
	private String msisdn;
	@JsonProperty("msg")
	private String Msg;
	@JsonProperty("senderId")
	private String senderId;
	@JsonProperty("customerid")
	private String customerid;
	@JsonProperty("customerName")
	private String customerName;
	@JsonProperty("actiontypes")
	private String actiontypes;

}
