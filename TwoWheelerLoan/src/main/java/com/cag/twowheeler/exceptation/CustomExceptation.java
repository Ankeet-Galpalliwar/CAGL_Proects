package com.cag.twowheeler.exceptation;

public class CustomExceptation extends RuntimeException {
	

	private String errorCode;

	public CustomExceptation(String message, String errorCode) {
	        super(message);
	        this.errorCode = errorCode;
	    }


}
