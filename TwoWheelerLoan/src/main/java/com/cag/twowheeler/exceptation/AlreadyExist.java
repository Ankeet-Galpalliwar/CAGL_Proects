package com.cag.twowheeler.exceptation;

import lombok.Getter;

@Getter
public class AlreadyExist extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String errorCode;

	public AlreadyExist(String message, String errorCode) {
	        super(message+" Already Exist..!");
	        this.errorCode = errorCode;
	    }

}
