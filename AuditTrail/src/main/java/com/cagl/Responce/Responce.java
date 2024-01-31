package com.cagl.Responce;

import lombok.*;

@Data
@Builder
public class Responce {

	private Boolean error;
	private Object Data;
	private String message;

}
