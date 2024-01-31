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
public class Voterauthresponce {

	@JsonProperty("http_response_code")
	private int http_response_code;

	@JsonProperty("result_code")
	private int result_code;

	@JsonProperty("RequestID")
	private String RequestID;

	@JsonProperty("result")
	private result result;

	@JsonProperty("client_ref_num")
	private String client_ref_num;

}
