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

import com.ecom.dto.CustomerDto;
import com.ecom.services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(
		method = { RequestMethod.POST },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CustomerDto save(HttpServletRequest request, HttpServletResponse response, @RequestBody CustomerDto customer) {
		return customerService.save(customer);
	}

	@RequestMapping(
		value = "/{customerId}",
		method = { RequestMethod.GET })
	@ResponseBody
	public CustomerDto get(@PathVariable("customerId") String customerId) {
		return customerService.get(customerId);
	}

	@RequestMapping(
		method = { RequestMethod.GET })
	@ResponseBody
	public List<CustomerDto> get(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		return customerService.getAll(pageNumber, pageSize);
	}

	@RequestMapping(
		value = "/{customerId}",
		method = { RequestMethod.DELETE })
	@ResponseBody
	public CustomerDto delete(@PathVariable("customerId") String customerId, HttpServletResponse response) {
		return customerService.delete(customerId);
	}

	@RequestMapping(
		value = "/{customerId}",
		method = { RequestMethod.PUT },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CustomerDto update(@PathVariable("customerId") String customerId, @RequestBody CustomerDto customer) {
		return customerService.update(customer);
	}

}
