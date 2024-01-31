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
public class voterauth {
	
	@JsonProperty("RequestID")
	private String RequestID;
	
	@JsonProperty("ID")
	private String ID;
	
}
