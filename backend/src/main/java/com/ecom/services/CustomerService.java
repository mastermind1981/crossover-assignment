package com.ecom.services;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ecom.convertors.DtoConvertor;
import com.ecom.dto.CustomerDto;
import com.ecom.exceptions.DuplicateEntryException;
import com.ecom.exceptions.InvalidCreditLimitException;
import com.ecom.exceptions.InvalidDataException;
import com.ecom.exceptions.NotFoundException;
import com.ecom.model.CustomerCredit;
import com.ecom.repository.CustomerCreditRepository;
import com.ecom.repository.CustomerRepository;
import com.ecom.exceptions.DataIntegrityException;

@Service
public class CustomerService {

	final static Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerCreditRepository customerCreditRepository;

	/**
	 * This method will save the details of Customer.
	 * 
	 * @param customerDto
	 * @return
	 */
	public CustomerDto save(CustomerDto customerDto) {
		try {
			CustomerCredit customerCredit = customerCreditRepository.findOne(customerDto.getCode());
			if (customerCredit != null) {
				customerDto.setCurrentCredit(customerCredit.getCurrentCredit());
				return update(customerDto);
			} else {
				customerCredit = DtoConvertor.buildCustomerCredit(customerDto);
				customerCredit.setCurrentCredit(0.0);
				customerCredit.setCreation_ts(Calendar.getInstance());
				customerCredit.setModification_ts(Calendar.getInstance());
				customerCreditRepository.save(customerCredit);
				return DtoConvertor.buildCustomerDto(customerCredit);
			}

		} catch (DuplicateEntryException exception) {
			throw exception;
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Exception occured {}", exception);
			throw new InvalidDataException();
		}
	}

	/**
	 * Method will get the details of the User.
	 * 
	 * @param customerId
	 * @return
	 */
	public CustomerDto get(String customerId) {
		CustomerDto customer = null;
		CustomerCredit customerCredit = null;
		try {
			customerCredit = customerCreditRepository.findOne(customerId);
			if (customerCredit == null) {
				throw new NotFoundException();
			} else {
				customer = DtoConvertor.buildCustomerDto(customerCredit);
			}
		} catch (NotFoundException exception) {
			throw exception;
		} catch (Exception exception) {
			logger.error("Exception occured {}", exception);
			throw new InvalidDataException();
		}
		return customer;
	}

	/**
	 * Method will delete customer from database.
	 * 
	 * @param customerId
	 * @return
	 */
	public CustomerDto delete(String customerId) {
		CustomerDto customerDto = null;
		CustomerCredit customerCredit = null;
		try {
			customerCredit = customerCreditRepository.findOne(customerId);
			if (customerCredit == null) {
				throw new NotFoundException();
			} else {
				customerRepository.delete(customerId);
				customerDto = DtoConvertor.buildCustomerDto(customerCredit);
			}
		} catch (NotFoundException exception) {
			throw exception;
		} catch (DataIntegrityViolationException exception) {
			logger.error("Exception occured {}", exception);
			throw new DataIntegrityException();
		} catch (Exception exception) {
			logger.error("Exception occured {}", exception);
			throw new InvalidDataException();
		}
		return customerDto;
	}

	/**
	 * This will update the customer details.
	 * 
	 * @param customer
	 * @return
	 */
	public CustomerDto update(CustomerDto customer) {
		CustomerCredit customerCredit = null;
		synchronized (customer.getCode()) {
			try {
				customerCredit = customerCreditRepository.findOne(customer.getCode());
				if (customerCredit == null) {
					throw new NotFoundException();
				} else {
					if (customer.getCreditLimit() < customerCredit.getCurrentCredit()) {
						throw new InvalidCreditLimitException();
					} else {
						customerCredit.setCreditLimit(customer.getCreditLimit());
						customerCredit.setCurrentCredit(customer.getCurrentCredit());
						customerCredit.setModification_ts(Calendar.getInstance());
						customerCredit.setAddress(customer.getAddress());
						customerCredit.setPhone1(customer.getPhone1());
						customerCredit.setPhone2(customer.getPhone2());
						customerCredit.setName(customer.getName());
						customerCredit.setModification_ts(Calendar.getInstance());

						customerCreditRepository.save(customerCredit);
						return DtoConvertor.buildCustomerDto(customerCredit);
					}
				}
			} catch (NotFoundException exception) {
				throw exception;
			} catch (Exception exception) {
				logger.error("Exception occured {}", exception);
				throw new InvalidDataException();
			}
		}
	}

	public List<CustomerDto> getAll(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		List<CustomerCredit> customerCreditList = customerCreditRepository.findAll(pageRequest);
		if (customerCreditList == null || customerCreditList.size() == 0) {
			throw new NotFoundException();
		}
		return DtoConvertor.buildCustomerDtoList(customerCreditList);
	}

}
