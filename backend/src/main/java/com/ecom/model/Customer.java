package com.ecom.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(
	name = "customer")
@Inheritance(
	strategy = InheritanceType.JOINED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(
		name = "customer_id")
	private String code;

	private String name;

	private String address;

	private String phone1;

	private String phone2;

	@Column(
		name = "credit_limit")
	private Double creditLimit;

	@OneToMany(
		mappedBy = "customer",
		fetch = FetchType.LAZY)
	private List<SaleOrder> saleorders;

	@Column(
		name = "modification_ts",
		insertable = false,
		updatable = true)
	private Calendar modification_ts;

	@Column(
		columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
		insertable = true,
		updatable = false)
	private Calendar creation_ts;

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

	public List<SaleOrder> getSaleorders() {
		return saleorders;
	}

	public void setSaleorders(List<SaleOrder> saleorders) {
		this.saleorders = saleorders;
	}

	public Customer() {

	}

	public Customer(String code, String name, String address, String phone1, String phone2, Double creditLimit) {
		super();
		this.code = code;
		this.name = name;
		this.address = address;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.creditLimit = creditLimit;
	}

	public Calendar getCreation_ts() {
		return creation_ts;
	}

	public void setCreation_ts(Calendar creation_ts) {
		this.creation_ts = creation_ts;
	}

	@PrePersist
	void onCreate() {
		this.setCreation_ts(Calendar.getInstance());
	}

	@PreUpdate
	void onPersist() {
		this.setModification_ts(Calendar.getInstance());
	}

	public Calendar getModification_ts() {
		return modification_ts;
	}

	public void setModification_ts(Calendar modification_ts) {
		this.modification_ts = modification_ts;
	}

}
