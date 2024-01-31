package com.cagl.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

	private boolean error;
	private String message;
	private Object data;

}
