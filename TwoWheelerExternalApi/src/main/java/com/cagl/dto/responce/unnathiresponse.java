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
public class unnathiresponse {
	
	@JsonProperty("loanId")
	private String loanId;
	
	@JsonProperty("statusCode")
	private String statusCode;

}
