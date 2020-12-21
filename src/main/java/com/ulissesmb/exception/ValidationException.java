package com.ulissesmb.exception;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1494395050650198476L;
	
	private String code;

    public ValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
