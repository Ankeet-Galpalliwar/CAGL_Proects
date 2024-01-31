package com.cagl.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Responce {
	private Boolean Error;
	private String message;
	private Object data;
}
