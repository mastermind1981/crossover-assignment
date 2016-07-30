package com.dev.frontend.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaleOrderDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderNumber;

	private CustomerDto customer;

	private Double totalPrice;

	private List<OrderLinesDto> orderLines = new ArrayList<OrderLinesDto>();

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderLinesDto> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLinesDto> orderLines) {
		this.orderLines = orderLines;
	}

}
