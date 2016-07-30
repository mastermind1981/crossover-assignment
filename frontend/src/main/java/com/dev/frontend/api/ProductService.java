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
import com.dev.frontend.dto.ProductDto;
import com.dev.frontend.utils.RestTemplateUtility;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * This service will communicate with backend
 * 
 * @author raghunandangupta
 *
 */
public class ProductService {

	private static class ProductServiceHelper {
		final static ProductService productService = new ProductService();
	}

	public static ProductService getProductService() {
		return ProductServiceHelper.productService;
	}

	private ProductService() {

	}

	private static Gson gson = new Gson();

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProductDto> fetchProductData() {
		List<ProductDto> productDataList = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			ResponseEntity<String> serviceResponse = RestTemplateUtility.getRestTemplate().exchange(
					new RequestEntity<String>(headers, HttpMethod.GET, new URI(Constants.PRODUCT_URI + "?pageSize=100000&pageNumber=0")),
					String.class);
			String line = serviceResponse.getBody();
			if (!(line != null && line.indexOf("message") != -1)) {
				List<LinkedTreeMap<String, Object>> productDtoList = new ArrayList<LinkedTreeMap<String, Object>>();
				productDtoList = gson.fromJson(line, productDtoList.getClass());
				productDtoList.removeAll(Collections.singleton(null));
				productDataList = new ArrayList<ProductDto>();
				for (int i = 0; i < productDtoList.size(); i++) {
					LinkedTreeMap<String, Object> productDto = productDtoList.get(i);
					productDataList.add(new ProductDto(productDto.get("code") + "", productDto.get("description") + "",
							Double.parseDouble(productDto.get("price") + ""), ((Double)productDto.get("quantity")).longValue()));
				}
			}
			System.out.println("#########" + line);
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return productDataList;
	}

	/**
	 * @param code
	 * @param description
	 * @param price
	 * @param initialQuantity
	 * @return
	 */
	public ProductDto saveProductDto(ProductDto productDto) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<ProductDto> entity = new HttpEntity<ProductDto>(productDto, headers);
			ResponseEntity<String> productResponse = RestTemplateUtility.getRestTemplate().postForEntity(Constants.PRODUCT_URI, entity,
					String.class);
			String line = productResponse.getBody();
			System.out.println("Response : " + line);
			if (!(line != null && line.indexOf("message") != -1)) {
				productDto = gson.fromJson(line, ProductDto.class);
			}
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return productDto;
	}

	/**
	 * @param code
	 * @return
	 */
	public ProductDto fetchProductDataByCode(String code) {
		ProductDto productDto = null;
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			ResponseEntity<String> serviceResponse = RestTemplateUtility.getRestTemplate()
					.exchange(new RequestEntity<String>(headers, HttpMethod.GET, new URI(Constants.PRODUCT_URI + "/" + code)), String.class);
			String line = serviceResponse.getBody();
			if (!(line != null && line.indexOf("message") != -1)) {
				productDto = gson.fromJson(line, ProductDto.class);
			}
			System.out.println("Response : " + line);
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return productDto;
	}

	/**
	 * This method will delete customer by code.
	 * 
	 * @param code
	 * @return
	 */
	public Boolean deleteProductDataByCode(String code) {
		try {
			RestTemplateUtility.getRestTemplate().delete(new URI(Constants.PRODUCT_URI + "/" + code));
			ProductDto productDto = fetchProductDataByCode(code);
			if (productDto == null) {
				return true;
			}
		} catch (Exception exception) {
			System.out.println("Exception occured : {}" + exception);
		}
		return false;
	}
}
