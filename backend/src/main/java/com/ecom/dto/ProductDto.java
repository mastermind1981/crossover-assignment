package com.ecom.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

	private String description;

	private Double price;

	private Long quantity;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long initialQuantity) {
		this.quantity = initialQuantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDto other = (ProductDto) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public ProductDto() {

	}

	public ProductDto(String code, String description, Double price, Long initialQuantity) {
		super();
		this.code = code;
		this.description = description;
		this.price = price;
		this.quantity = initialQuantity;
	}

	public ProductDto(String code) {
		super();
		this.code = code;
	}

	public ProductDto(String code, Long quantity) {
		super();
		this.code = code;
		this.quantity = quantity;
	}

}
