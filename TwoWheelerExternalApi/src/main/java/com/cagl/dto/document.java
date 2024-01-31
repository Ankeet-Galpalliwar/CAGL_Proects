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
public class document {

	@JsonProperty("docType")
	private String docType;

	@JsonProperty("docId")
	private String docId;
}
