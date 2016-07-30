package com.ecom.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecom.dto.ErrorResource;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ NotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundRequest(RuntimeException e, WebRequest request) {
		NotFoundException ire = (NotFoundException) e;
		ErrorResource error = new ErrorResource("Not Found", "Target Entity Not Found.");
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.NOT_FOUND, request);
	}

	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ InvalidDataException.class })
	protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
		InvalidDataException ire = (InvalidDataException) e;
		ErrorResource error = new ErrorResource("Invalid Data", "Data is not valid");
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ DuplicateEntryException.class })
	protected ResponseEntity<Object> handleDuplicateEntry(RuntimeException e, WebRequest request) {
		DuplicateEntryException ire = (DuplicateEntryException) e;
		ErrorResource error = new ErrorResource("Duplicate Entry", "Data is already present");
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ InvalidCreditLimitException.class })
	protected ResponseEntity<Object> handleInvalidCreditLimit(RuntimeException e, WebRequest request) {
		InvalidCreditLimitException ire = (InvalidCreditLimitException) e;
		ErrorResource error = new ErrorResource("Invalid Credit Limit", "Credit limit can not be decreased.");
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ OrderProcessException.class })
	protected ResponseEntity<Object> handleOrderProcess(RuntimeException e, WebRequest request) {
		OrderProcessException ire = (OrderProcessException) e;
		ErrorResource error = new ErrorResource("Insufficient Quantity / Credit Limit", "Sale order can not be created.");
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ DataIntegrityException.class })
	protected ResponseEntity<Object> handleSQLException(RuntimeException e, WebRequest request) {
		DataIntegrityException ire = (DataIntegrityException) e;
		ErrorResource error = new ErrorResource("Data integrity failed..", "Foreign key constraint failed.");
		error.setFieldErros(ire.getFieldErrors());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
	}
	
}
