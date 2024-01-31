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
public class ComboCbReportResponce {
	@JsonProperty("status")
	private String status;
	@JsonProperty("error")
	private String error;
	@JsonProperty("response")
	private response response;
	

}
