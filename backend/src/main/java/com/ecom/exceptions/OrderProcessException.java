package com.ecom.exceptions;

import java.util.List;

import com.ecom.dto.FieldErrorResource;

/**
 * @author raghunandangupta
 *
 */
public class OrderProcessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	List<FieldErrorResource> fieldErrors;
	
	public OrderProcessException() {
		super("Order can not be created.");
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
