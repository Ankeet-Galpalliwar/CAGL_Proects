package com.cagl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class address {

	@JsonProperty("addrType")
	private String addrType;
	@JsonProperty("addrLine1")
	private String addrLine1;
	@JsonProperty("city")
	private String city;
	@JsonProperty("state")
	private String state;
	@JsonProperty("pinCode")
	private String pinCode;

}
