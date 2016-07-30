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
import com.dev.frontend.dto.SaleOrderDto;
import com.dev.frontend.utils.RestTemplateUtility;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * This service will communicate with backend
 * 
 * @author raghunandangupta
 *
 */
public class SaleOrderService {

	private static class SaleOrderServiceHelper {
		final static SaleOrderService saleOrderService = new SaleOrderService();
	}

	/**
	 * @return
	 */
	public static SaleOrderService getSaleOrderService() {
		return SaleOrderServiceHelper.saleOrderService;
	}

	private SaleOrderService() {

	}

	private static Gson gson = new Gson();

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SaleOrderDto> fetchSaleOrderData() {
		List<SaleOrderDto> saleOrderList = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			ResponseEntity<String> serviceResponse = RestTemplateUtility.getRestTemplate().exchange(
					new RequestEntity<String>(headers, HttpMethod.GET, new URI(Constants.SALEORDER_URI + "?pageSize=100000&pageNumber=0")),
					String.class);
			String line = serviceResponse.getBody();
			if (!(line != null && line.indexOf("message") != -1)) {
				List<LinkedTreeMap<String, Object>> saleOrderDtoList = new ArrayList<LinkedTreeMap<String, Object>>();
				saleOrderDtoList = gson.fromJson(line, saleOrderDtoList.getClass());
				saleOrderDtoList.removeAll(Collections.singleton(null));
				saleOrderList = new ArrayList<SaleOrderDto>();
				for (int i = 0; i < saleOrderDtoList.size(); i++) {
					SaleOrderDto saleOrderDto = new SaleOrderDto();
					LinkedTreeMap<String, Object> saleorder = saleOrderDtoList.get(i);
					saleOrderDto.setOrderNumber(saleorder.get("orderNumber") + "");
					saleOrderDto.setTotalPrice(Double.parseDouble(saleorder.get("totalPrice") + ""));
					saleOrderDto.setCustomer(new CustomerDto(((LinkedTreeMap<String, Object>) saleorder.get("customer")).get("code") + "",
							((LinkedTreeMap<String, Object>) saleorder.get("customer")).get("name") + ""));
					saleOrderList.add(saleOrderDto);
				}
			}
			System.out.println("Sale Order Fetch response  : " + line);
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return saleOrderList;
	}

	/**
	 * @param saleOrderDto
	 * @return
	 */
	public SaleOrderDto saveSaleOrderDto(SaleOrderDto saleOrderDto) {

		System.out.println(new Gson().toJson(saleOrderDto));
		SaleOrderDto updatedSaleOrderDto = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<SaleOrderDto> entity = new HttpEntity<SaleOrderDto>(saleOrderDto, headers);
			ResponseEntity<String> productResponse = RestTemplateUtility.getRestTemplate().postForEntity(Constants.SALEORDER_URI, entity,
					String.class);
			String line = productResponse.getBody();
			System.out.println("Response : " + line);
			if (!(line != null && line.indexOf("message") != -1)) {
				updatedSaleOrderDto = gson.fromJson(line, SaleOrderDto.class);
			}
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return updatedSaleOrderDto;
	}

	/**
	 * @param code
	 * @return
	 */
	public SaleOrderDto fetchSaleOrderByCode(String code) {
		SaleOrderDto updatedSaleOrderDto = null;
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			ResponseEntity<String> serviceResponse = RestTemplateUtility.getRestTemplate()
					.exchange(new RequestEntity<String>(headers, HttpMethod.GET, new URI(Constants.SALEORDER_URI + "/" + code)), String.class);
			String line = serviceResponse.getBody();
			if (!(line != null && line.indexOf("message") != -1)) {
				updatedSaleOrderDto = gson.fromJson(line, SaleOrderDto.class);
			}
			System.out.println("Response : " + line);
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return updatedSaleOrderDto;
	}

	/**
	 * This method will delete customer by code.
	 * 
	 * @param code
	 * @return
	 */
	public Boolean deleteSaleOrderByCode(String code) {
		try {
			RestTemplateUtility.getRestTemplate().delete(new URI(Constants.SALEORDER_URI + "/" + code));
			SaleOrderDto updatedSaleOrderDto = fetchSaleOrderByCode(code);
			if (updatedSaleOrderDto == null) {
				return true;
			}
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return false;
	}
}
