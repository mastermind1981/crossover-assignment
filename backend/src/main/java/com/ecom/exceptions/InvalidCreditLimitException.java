package com.ecom.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ecom.dto.FieldErrorResource;

@ResponseStatus(
	value = HttpStatus.NOT_FOUND,
	reason = "No such Entity")
public class InvalidCreditLimitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	List<FieldErrorResource> fieldErrors;
	
	public InvalidCreditLimitException() {
		super("Invalid Credit Limit.");
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
