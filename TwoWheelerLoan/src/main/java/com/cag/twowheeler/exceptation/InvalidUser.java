package com.cag.twowheeler.exceptation;

public class InvalidUser extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidUser() {
		super("Invalid Username and Password");
	}
}
