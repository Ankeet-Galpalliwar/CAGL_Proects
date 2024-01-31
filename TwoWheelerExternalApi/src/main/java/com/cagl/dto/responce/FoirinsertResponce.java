package com.cagl.dto.responce;

import java.util.List;

import com.cagl.dto.address;
import com.cagl.dto.document;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoirinsertResponce {

	@JsonProperty("msg")
	private String msg;

	@JsonProperty("status")
	private String status;
}
