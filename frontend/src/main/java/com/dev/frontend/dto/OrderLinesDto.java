package com.dev.frontend.dto;

import java.io.Serializable;

public class OrderLinesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProductDto product;

	private Long quantity;

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public OrderLinesDto() {

	}

	public OrderLinesDto(ProductDto product, Long quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

}
