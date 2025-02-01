package com.example.order.exception;

import java.text.MessageFormat;

import com.example.order.dto.Error;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 644766533160217585L;

	public static final String ERR_CODE = "resource.not.found.{0}";
	public static final String ERR_DESC = "Resource not found.";
	
	private final transient Error error;
	
	public ResourceNotFoundException(String resource) {
		this.error = new Error(new MessageFormat(ERR_CODE).format(new String[] {resource}), ERR_DESC, null, false);
	}
	
	public Error getError() {
		return error;
	}
}
