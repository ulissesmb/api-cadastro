package com.ulissesmb.exception;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 5912951469465014636L;

	private String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
