package com.ulissesmb.exception;

public class NotFoundException extends BusinessException{

	private static final long serialVersionUID = -8044379981917628005L;

	private static final String MESSAGE = "Nenhum registro encontrado!";

    public NotFoundException() {
        super("" + MESSAGE.hashCode(), MESSAGE);
    }

    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
