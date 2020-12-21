package com.ulissesmb.response;

public class _Error {

	private final String code;
	
	private final String description;

	public _Error(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}
	
	
}
