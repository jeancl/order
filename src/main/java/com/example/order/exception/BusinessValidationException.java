package com.example.order.exception;

import java.text.MessageFormat;

import com.example.order.dto.Error;

public class BusinessValidationException extends RuntimeException {

	private static final long serialVersionUID = 644766533160217585L;

	public static final String ERR_CODE = "{0}.invalid";
	
	private final transient Error error;
	
	public BusinessValidationException(String resource, String description) {
		this.error = new Error(new MessageFormat(ERR_CODE).format(new String[] {resource}), description, null, false);
	}
	
	public BusinessValidationException(String resource, String description, String details) {
		this.error = new Error(new MessageFormat(ERR_CODE).format(new String[] {resource}), description, details, false);
	}
	
	public Error getError() {
		return error;
	}
}
