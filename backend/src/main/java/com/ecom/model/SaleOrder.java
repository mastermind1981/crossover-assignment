package com.ecom.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(
	name = "sale_order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(
		name = "sale_order_number")
	private String orderNumber;

	@OneToOne
	@JoinColumn(
		name = "customer_id")
	private Customer customer;

	@Column(
		name = "total_price")
	private Double totalPrice;

	@OneToMany(
		mappedBy = "saleOrder",
		fetch = FetchType.EAGER,
		orphanRemoval = true,
		cascade = CascadeType.REMOVE)
	private List<OrderLines> orderLines;

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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderLines> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLines> orderLines) {
		this.orderLines = orderLines;
	}

	public Calendar getCreation_ts() {
		return creation_ts;
	}

	public void setCreation_ts(Calendar creation_ts) {
		this.creation_ts = creation_ts;
	}

	public Calendar getModification_ts() {
		return modification_ts;
	}

	public void setModification_ts(Calendar modification_ts) {
		this.modification_ts = modification_ts;
	}
	
	@PrePersist
	void onCreate() {
		this.setCreation_ts(Calendar.getInstance());
	}

	@PreUpdate
	void onPersist() {
		this.setModification_ts(Calendar.getInstance());
	}

}
