package com.ecom.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(
	name = "customer_credit")
@PrimaryKeyJoinColumn(
	name = "customer_id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerCredit extends Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double currentCredit;

	public Double getCurrentCredit() {
		return currentCredit;
	}

	public void setCurrentCredit(Double currentCredit) {
		this.currentCredit = currentCredit;
	}

	public CustomerCredit() {

	}

	public CustomerCredit(String code, String name, String address, String phone1, String phone2, Double creditLimit, Double currentCredit) {
		super(code, name, address, phone1, phone2, creditLimit);
		this.currentCredit = currentCredit;
	}

}
