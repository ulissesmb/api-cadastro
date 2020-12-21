package com.ulissesmb.exception;

import java.text.MessageFormat;

public class InvalidHeaderException extends HeaderException{

	private static final long serialVersionUID = 1327579013832959467L;
	
	private static final String MESSAGE = "Header {0} is invalid!";

    public InvalidHeaderException(String code, String message) {
        super(code, message);
    }

    public InvalidHeaderException(String field) {
        this("IH"+field.hashCode(), MessageFormat.format(MESSAGE, field));
    }
}
