package com.ecom.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecom.dto.ProductDto;
import com.ecom.services.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(
		method = { RequestMethod.POST },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ProductDto save(@RequestBody ProductDto product, HttpServletRequest request, HttpServletResponse response) {
		return productService.save(product);
	}

	@RequestMapping(
		value = { "/{productId}" },
		method = { RequestMethod.GET },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ProductDto get(@PathVariable("productId") String productId) {
		return productService.get(productId);
	}

	@RequestMapping(
		value = { "/{productId}" },
		method = { RequestMethod.DELETE })
	@ResponseBody
	public ProductDto delete(@PathVariable("productId") String productId) {
		return productService.delete(productId);
	}

	@RequestMapping(
		value = { "/{productId}" },
		method = { RequestMethod.PUT },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ProductDto update(@PathVariable("saleOrderId") String saleOrderId, ProductDto product, HttpServletRequest request,
			HttpServletResponse response) {
		return productService.save(product);
	}

	@RequestMapping(
		method = { RequestMethod.GET })
	@ResponseBody
	public List<ProductDto> get(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		return productService.getAll(pageNumber, pageSize);
	}
}
