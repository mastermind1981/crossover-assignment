
package com.ecom.exceptions;

import java.util.List;

import com.ecom.dto.FieldErrorResource;

public class DuplicateEntryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	List<FieldErrorResource> fieldErrors;

	public DuplicateEntryException() {
		super("Duplicate entry exists.");
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
