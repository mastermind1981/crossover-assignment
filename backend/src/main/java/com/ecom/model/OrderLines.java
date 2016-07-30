package com.ecom.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(
	name = "order_lines")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderLines implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(
		fetch = FetchType.EAGER)
	@JoinColumn(
		name = "product_code",
		nullable = false)
	private Product product;

	private Long quantity;

	@ManyToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.EAGER)
	@JoinColumn(
		name = "sale_order_number",
		nullable = false)
	private SaleOrder saleOrder;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

}
