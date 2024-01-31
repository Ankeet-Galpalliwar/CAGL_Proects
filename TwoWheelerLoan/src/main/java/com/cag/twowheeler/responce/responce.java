package com.cag.twowheeler.responce;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class responce {
	
	private String error;
	private Object data;
	private String message;

}
