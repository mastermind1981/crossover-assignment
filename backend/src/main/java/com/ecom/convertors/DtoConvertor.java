package com.ecom.convertors;

import java.util.ArrayList;
import java.util.List;

import com.ecom.dto.CustomerDto;
import com.ecom.dto.OrderLinesDto;
import com.ecom.dto.ProductDto;
import com.ecom.dto.SaleOrderDto;
import com.ecom.model.Customer;
import com.ecom.model.CustomerCredit;
import com.ecom.model.OrderLines;
import com.ecom.model.Product;
import com.ecom.model.SaleOrder;

public class DtoConvertor {

	public static Customer buildCustomer(CustomerDto customerDto) {
		Customer Customer = new Customer(customerDto.getCode(), customerDto.getName(), customerDto.getAddress(), customerDto.getPhone1(),
				customerDto.getPhone1(), customerDto.getCreditLimit());
		return Customer;
	}

	public static CustomerCredit buildCustomerCredit(CustomerDto customerDto) {
		CustomerCredit Customer = new CustomerCredit(customerDto.getCode(), customerDto.getName(), customerDto.getAddress(), customerDto.getPhone1(),
				customerDto.getPhone1(), customerDto.getCreditLimit(), customerDto.getCreditLimit());
		return Customer;
	}

	public static CustomerDto buildCustomerDto(CustomerCredit customerCredit) {
		CustomerDto CustomerDto = new CustomerDto(customerCredit.getCode(), customerCredit.getName(), customerCredit.getAddress(),
				customerCredit.getPhone1(), customerCredit.getPhone2(), customerCredit.getCreditLimit(), customerCredit.getCurrentCredit());
		return CustomerDto;
	}

	public static CustomerDto buildCustomerDto(Customer customer) {
		CustomerDto CustomerDto = new CustomerDto(customer.getCode(), customer.getName(), customer.getAddress(), customer.getPhone1(),
				customer.getPhone1(), customer.getCreditLimit());
		return CustomerDto;
	}

	public static List<CustomerDto> buildCustomerDtoList(List<CustomerCredit> customerCreditList) {
		List<CustomerDto> customerDtoList = new ArrayList<CustomerDto>();
		for (CustomerCredit customerCredit : customerCreditList) {
			customerDtoList.add(buildCustomerDto(customerCredit));
		}
		return customerDtoList;
	}

	public static SaleOrderDto buildSaleOrderDto(SaleOrder saleOrder) {
		SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setOrderNumber(saleOrder.getOrderNumber());
		saleOrderDto.setCustomer(buildCustomerDto(saleOrder.getCustomer()));
		saleOrderDto.setTotalPrice(saleOrder.getTotalPrice());

		for (OrderLines orderLines : saleOrder.getOrderLines()) {
			saleOrderDto.getOrderLines().add(buildOrderLinesDto(orderLines));
		}
		return saleOrderDto;
	}

	public static ProductDto buildProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setCode(product.getCode());
		productDto.setDescription(product.getDescription());
		productDto.setQuantity(product.getQuantity());
		productDto.setPrice(product.getPrice());
		return productDto;
	}
	
	public static Product buildProduct(ProductDto productDto) {
		Product product = new Product();
		product.setCode(productDto.getCode());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setQuantity(productDto.getQuantity());
		return product;
	}

	public static OrderLinesDto buildOrderLinesDto(OrderLines orderLines) {
		OrderLinesDto orderLinesDto = new OrderLinesDto();
		orderLinesDto.setProduct(buildProductDto(orderLines.getProduct()));
		orderLinesDto.setQuantity(orderLines.getQuantity());
		return orderLinesDto;
	}
}
