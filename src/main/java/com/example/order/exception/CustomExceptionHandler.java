package com.example.order.exception;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.order.dto.Error;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String INVALID_DATA_ERR_CODE = "invalid.data.{0}";
	private static final String INVALID_DATA_ERR_DESC = "Field invalid data";
	
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
		return handleExceptionInternal(e, e.getError(), buildDefaultHttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(BusinessValidationException.class)
	protected ResponseEntity<Object> handleBusinessValidationException(BusinessValidationException e, WebRequest request) {
		return handleExceptionInternal(e, e.getError(), buildDefaultHttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
		
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<Error> errorList = e.getBindingResult().getFieldErrors().stream().map(fieldError -> 
			new Error(new MessageFormat(INVALID_DATA_ERR_CODE).format(new String[] {fieldError.getField()}), INVALID_DATA_ERR_DESC, fieldError.getDefaultMessage(), false)
		).toList();
		return handleExceptionInternal(e, errorList, buildDefaultHttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private HttpHeaders buildDefaultHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
