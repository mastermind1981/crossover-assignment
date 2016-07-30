package com.ecom.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(
	name = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(
		name = "product_code")
	private String code;

	private String description;

	private Double price;

	@Column(
		name = "modification_ts",insertable=false,updatable=true)
	private Calendar modification_ts;

	@Column(
		columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable=true,updatable=false)
	private Calendar creation_ts;

	@Column(
		name = "quantity")
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

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
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
		Product other = (Product) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public Calendar getModification_ts() {
		return modification_ts;
	}

	public void setModification_ts(Calendar modification_ts) {
		this.modification_ts = modification_ts;
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
}
