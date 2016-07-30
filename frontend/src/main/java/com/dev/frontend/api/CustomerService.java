package com.dev.frontend.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.dev.frontend.constants.Constants;
import com.dev.frontend.dto.CustomerDto;
import com.dev.frontend.utils.RestTemplateUtility;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * This service will communicate with backend
 * 
 * @author raghunandangupta
 *
 */
public class CustomerService {

	private static class CustomerServiceHelper {
		final static CustomerService customerService = new CustomerService();
	}

	public static CustomerService getCustomerService() {
		return CustomerServiceHelper.customerService;
	}

	private CustomerService() {

	}

	private static Gson gson = new Gson();

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerDto> fetchCustomerData() {
		List<CustomerDto> customerDataList = null;
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			ResponseEntity<String> serviceResponse = RestTemplateUtility.getRestTemplate().exchange(
					new RequestEntity<String>(headers, HttpMethod.GET, new URI(Constants.CUSTOMER_URI + "?pageSize=100000&pageNumber=0")),
					String.class);
			String line = serviceResponse.getBody();
			if (!(line != null && line.indexOf("message") != -1)) {				List<LinkedTreeMap<String, Object>> customerDtoList = new ArrayList<LinkedTreeMap<String, Object>>();
				customerDtoList = gson.fromJson(line, customerDtoList.getClass());
				customerDtoList.removeAll(Collections.singleton(null));
				customerDataList = new ArrayList<CustomerDto>();
				for (int i = 0; i < customerDtoList.size(); i++) {
					LinkedTreeMap<String, Object> customerDto = customerDtoList.get(i);
					customerDataList.add(new CustomerDto(customerDto.get("code") + "", customerDto.get("name") + "", customerDto.get("address") + "",
							customerDto.get("phone1") + "", customerDto.get("phone2") + "", Double.parseDouble(customerDto.get("creditLimit") + ""),
							Double.parseDouble(customerDto.get("currentCredit") + "")));
				}
			}
			System.out.println("Customer Data Response : " + line);
		} catch (Exception exception) {
			System.out.println("Exception occured :" + exception);
		}
		return customerDataList;
	}

	/**
	 * @param code
	 * @param name
	 * @param phone1
	 * @param phone2
	 * @param address
	 * @param creditLimitF
	 * @return
	 */
	public CustomerDto saveCustomerDto(CustomerDto customerDto) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<CustomerDto> entity = new HttpEntity<CustomerDto>(customerDto, headers);
			ResponseEntity<String> customerResponse = RestTemplateUtility.getRestTemplate().postForEntity(Constants.CUSTOMER_URI, entity,
					String.class);
			String line = customerResponse.getBody();
			System.out.println("Response : " + line);
			if (!(line != null && line.indexOf("message") != -1)) {
				customerDto = gson.fromJson(line, CustomerDto.class);
			}
		} catch (Exception exception) {
			System.out.println("Exception occured : " + exception);
		}
		return customerDto;
	}

	/**
	 * @param code
	 * @return
	 */
	public CustomerDto fetchCustomerDataByCode(String code) {
		CustomerDto customerDto = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			ResponseEntity<String> serviceResponse = RestTemplateUtility.getRestTemplate()
					.exchange(new RequestEntity<String>(headers, HttpMethod.GET, new URI(Constants.CUSTOMER_URI + "/" + code)), String.class);
			String line = serviceResponse.getBody();
			if (!(line != null && line.indexOf("message") != -1)) {
				customerDto = gson.fromJson(line, CustomerDto.class);
			}
			System.out.println("Response : " + line);
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return customerDto;
	}

	/**
	 * This method will delete customer by code.
	 * 
	 * @param code
	 * @return
	 */
	public Boolean deleteCustomerDataByCode(String code) {
		try {
			RestTemplateUtility.getRestTemplate().delete(new URI(Constants.CUSTOMER_URI + "/" + code));
			CustomerDto customerDto = fetchCustomerDataByCode(code);
			if (customerDto == null) {
				return true;
			}
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return false;
	}
}
