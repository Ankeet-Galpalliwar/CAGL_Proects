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
public class response {
	
	@JsonProperty("requestId")
	private String requestId;
	@JsonProperty("cbStatus")
	private String cbStatus;
	@JsonProperty("reqDate")
	private String reqDate;
	@JsonProperty("failureReason")
	private String failureReason;
	@JsonProperty("nBFC_MFICount")
	private String nBFC_MFICount;
	@JsonProperty("tLInstCount")
	private String tLInstCount;
	@JsonProperty("totalOutAmnt")
	private String totalOutAmnt;
	@JsonProperty("totalOvrDueAmnt")
	private String totalOvrDueAmnt;
	@JsonProperty("wrtOffAmnt")
	private String wrtOffAmnt;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("apiStatus")
	private String apiStatus;
	@JsonProperty("final_decision")
	private String final_decision;
	@JsonProperty("llink")
	private String llink;

}
