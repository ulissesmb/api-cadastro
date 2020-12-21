package com.ulissesmb.exception;

import java.text.MessageFormat;

public class RequiredHeaderException extends HeaderException{

	private static final long serialVersionUID = 1903437817602600235L;
		
	private static final String MESSAGE = "Header {0} é obrigatorio!";

    public RequiredHeaderException(String code, String message) {
        super(code, message);
    }

    public RequiredHeaderException(String field) {
        this("RH"+field.hashCode(), MessageFormat.format(MESSAGE, field));
    }
}
