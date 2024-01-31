package com.cagl.Responce;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class smsResponce {
	
	@JsonProperty("status_code")
	private String status_code;
	
	@JsonProperty("transid")
	private String transid;
	
	@JsonProperty("status_desc")
	private String status_desc;
}
