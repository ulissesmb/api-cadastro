package com.ulissesmb.exception;

import java.text.MessageFormat;

public class HeaderException extends BusinessException{

	private static final long serialVersionUID = -857503254348235213L;
	
	private static final String MESSAGE = "Header {0} is required!";

    public HeaderException(String code, String message) {
        super(code, message);
    }

    public HeaderException(String field) {
        this("H"+field.hashCode(), MessageFormat.format(MESSAGE, field));
    }
}
