package com.ecom.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ecom.convertors.DtoConvertor;
import com.ecom.dto.OrderLinesDto;
import com.ecom.dto.SaleOrderDto;
import com.ecom.exceptions.InvalidDataException;
import com.ecom.exceptions.NotFoundException;
import com.ecom.exceptions.OrderProcessException;
import com.ecom.model.CustomerCredit;
import com.ecom.model.OrderLines;
import com.ecom.model.Product;
import com.ecom.model.SaleOrder;
import com.ecom.repository.CustomerCreditRepository;
import com.ecom.repository.OrderLinesRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.SaleOrderRepository;

/**
 * @author raghunandangupta
 *
 */
@Service
public class SaleOrderService {

	@Autowired
	SaleOrderRepository saleOrderRepository;

	@Autowired
	private CustomerCreditRepository customerCreditRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderLinesRepository orderLinesRepository;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;

	/**
	 * @param saleOrderDto
	 * @return
	 */
	@Transactional(
		rollbackOn = { RuntimeException.class })
	public SaleOrderDto save(SaleOrderDto saleOrderDto) {
		SaleOrder saleOrder = null;
		try {
			Product savedProduct = null;
			CustomerCredit customerCredit = null;
			Double creditLimit = 0d;
			saleOrder = saleOrderRepository.findOne(saleOrderDto.getOrderNumber());
			customerCredit = customerCreditRepository.findOne(saleOrderDto.getCustomer().getCode());
			Calendar calendar = null;
			if (saleOrder != null) {
				calendar = Calendar.getInstance();
				creditLimit = saleOrder.getTotalPrice();
				for (OrderLines orderLines : saleOrder.getOrderLines()) {
					Product product = orderLines.getProduct();
					savedProduct = productRepository.findOne(product.getCode());
					if (savedProduct == null) {
						throw new NotFoundException();
					}
					savedProduct.setQuantity(savedProduct.getQuantity() + orderLines.getQuantity());
					productRepository.save(savedProduct);
				}
				saleOrder.getOrderLines().removeAll(Collections.singleton(null));
				saleOrderRepository.delete(saleOrderDto.getOrderNumber());

				customerCredit.setCurrentCredit(customerCredit.getCurrentCredit() - creditLimit.floatValue());
				customerCreditRepository.save(customerCredit);
			}

			saleOrder = new SaleOrder();
			saleOrder.setOrderNumber(saleOrderDto.getOrderNumber());
			saleOrder.setCustomer(customerCreditRepository.findOne(saleOrderDto.getCustomer().getCode()));
			saleOrder.setTotalPrice(saleOrderDto.getTotalPrice());
			if (calendar != null)
				saleOrder.setModification_ts(Calendar.getInstance());
			saleOrder.setModification_ts(Calendar.getInstance());
			saleOrder = saleOrderRepository.save(saleOrder);

			List<OrderLines> orderLines = new ArrayList<OrderLines>();
			for (OrderLinesDto orderLinesDto : saleOrderDto.getOrderLines()) {
				OrderLines orderLine = new OrderLines();
				orderLine.setProduct(productRepository.findOne(orderLinesDto.getProduct().getCode()));
				orderLine.setQuantity(orderLinesDto.getQuantity());
				orderLine.setSaleOrder(saleOrder);
				orderLines.add(orderLine);

				savedProduct = productRepository.findOne(orderLine.getProduct().getCode());
				if (savedProduct.getQuantity() >= orderLine.getQuantity()) {
					savedProduct.setQuantity(savedProduct.getQuantity() - orderLine.getQuantity());
					productService.update(DtoConvertor.buildProductDto(savedProduct));
				} else {
					throw new OrderProcessException();
				}
			}

			if (saleOrder.getTotalPrice() <= customerCredit.getCreditLimit() - customerCredit.getCurrentCredit()) {

				customerCredit.setCurrentCredit(customerCredit.getCurrentCredit() + saleOrder.getTotalPrice().floatValue());
				customerService.update(DtoConvertor.buildCustomerDto(customerCredit));

				orderLinesRepository.save(orderLines);
				saleOrder.setOrderLines(orderLines);
				return DtoConvertor.buildSaleOrderDto(saleOrder);
			} else {
				throw new OrderProcessException();
			}
		} catch (Exception exception) {
			throw new InvalidDataException();
		}
	}

	/**
	 * @param saleOrderId
	 * @return
	 */
	public SaleOrderDto get(String saleOrderId) {
		SaleOrder saleOrder = null;
		try {
			saleOrder = saleOrderRepository.findOne(saleOrderId);
			if (saleOrder == null) {
				throw new NotFoundException();
			}
			return DtoConvertor.buildSaleOrderDto(saleOrder);
		} catch (NotFoundException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new InvalidDataException();
		}
	}

	/**
	 * @param saleOrderId
	 * @return
	 */
	@Transactional(
			rollbackOn = { RuntimeException.class })
	public SaleOrderDto delete(String saleOrderId) {
		SaleOrder saleOrder = null;
		try {
			saleOrder = saleOrderRepository.findOne(saleOrderId);
			if (saleOrder == null) {
				throw new NotFoundException();
			} else {
				
				CustomerCredit customerCredit = customerCreditRepository.findOne(saleOrder.getCustomer().getCode());
				customerCredit.setCurrentCredit(customerCredit.getCurrentCredit()- saleOrder.getTotalPrice());
				customerService.update(DtoConvertor.buildCustomerDto(customerCredit));
				
				for(OrderLines orderLines : saleOrder.getOrderLines()){
					Product product = orderLines.getProduct();
					product.setQuantity(product.getQuantity() + orderLines.getQuantity());
					productService.update(DtoConvertor.buildProductDto(product));
				}
				
				saleOrder.getOrderLines().removeAll(Collections.singleton(null));
				saleOrderRepository.delete(saleOrderId);
				return DtoConvertor.buildSaleOrderDto(saleOrder);
			}
		} catch (NotFoundException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new InvalidDataException();
		}
	}

	/**
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<SaleOrderDto> findAll(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		List<SaleOrder> saleOrderList = saleOrderRepository.findAll(pageRequest);
		if (saleOrderList == null || saleOrderList.size() == 0) {
			throw new NotFoundException();
		} else {
			List<SaleOrderDto> saleOrderDtoList = new ArrayList<SaleOrderDto>();
			for (SaleOrder saleOrder : saleOrderList) {
				saleOrderDtoList.add(DtoConvertor.buildSaleOrderDto(saleOrder));
			}
			return saleOrderDtoList;
		}
	}

}
