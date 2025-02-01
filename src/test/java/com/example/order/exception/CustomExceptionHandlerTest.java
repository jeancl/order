package com.example.order.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import com.example.order.dto.Error;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

	@InjectMocks
	private CustomExceptionHandler customExceptionHandler;
	
	@Mock
	private WebRequest webRequestMock;
	
	@Mock
	private HttpHeaders headers;
	
	@Test
	void handleResourceNotFoundExceptionTest() {
		ResponseEntity<Object> response = customExceptionHandler.handleResourceNotFoundException(new ResourceNotFoundException("test"), webRequestMock);
	
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Error errorResponse = (Error) response.getBody();
		assertNotNull(errorResponse);
		assertFalse(errorResponse.isRecoverable());
		assertEquals("resource.not.found.test", errorResponse.getReasonCode());
	}
	
	@Test
	void handleBusinessValidationExceptionTest() {
		ResponseEntity<Object> response = customExceptionHandler.handleBusinessValidationException(new BusinessValidationException("test", "test"), webRequestMock);
	
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Error errorResponse = (Error) response.getBody();
		assertNotNull(errorResponse);
		assertFalse(errorResponse.isRecoverable());
		assertEquals("test.invalid", errorResponse.getReasonCode());
		assertEquals("test", errorResponse.getDescription());
		assertNull(errorResponse.getDetails());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void handleMethodArgumentNotValidTest() {
		BindingResult bindingResult = mock(BindingResult.class);
		FieldError fieldError = new FieldError("test", "amount", "must be greater than or equal to 1");
		when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
		MethodParameter methodParamert = mock(MethodParameter.class);
		
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParamert, bindingResult);
		
		ResponseEntity<Object> response = customExceptionHandler.handleMethodArgumentNotValid(exception, headers, HttpStatus.BAD_REQUEST, webRequestMock);
	
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		List<Error> errorResponse = (List<Error>) response.getBody();
		assertNotNull(errorResponse);
		assertFalse(errorResponse.get(0).isRecoverable());
		assertEquals("invalid.data.amount", errorResponse.get(0).getReasonCode());
		assertEquals("Field invalid data", errorResponse.get(0).getDescription());
		assertEquals("must be greater than or equal to 1", errorResponse.get(0).getDetails());
	}
}
