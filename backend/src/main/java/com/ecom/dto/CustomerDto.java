package com.ecom.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

	private String name;

	private String address;

	private String phone1;

	private String phone2;

	private Double creditLimit;

	private Double currentCredit;

	public CustomerDto() {

	}

	public CustomerDto(String code, String name, String address, String phone1, String phone2, Double creditLimit) {
		super();
		this.code = code;
		this.name = name;
		this.address = address;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.creditLimit = creditLimit;
	}

	public CustomerDto(String code, String name, String address, String phone1, String phone2, Double creditLimit, Double currentCredit) {
		super();
		this.code = code;
		this.name = name;
		this.address = address;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.creditLimit = creditLimit;
		this.currentCredit = currentCredit;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Double getCurrentCredit() {
		return currentCredit;
	}

	public void setCurrentCredit(Double currentCredit) {
		this.currentCredit = currentCredit;
	}

	public CustomerDto(String code) {
		super();
		this.code = code;
	}

}
