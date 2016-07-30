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

import com.ecom.dto.SaleOrderDto;
import com.ecom.services.SaleOrderService;

/**
 * This class will be used to create sale order.
 * 
 * @author raghunandangupta
 *
 */
@Controller
@RequestMapping("/saleorder")
public class SaleOrderController {

	@Autowired
	private SaleOrderService saleOrderService;

	/**
	 * This method will create / update the sale order.
	 * 
	 * @param saleOrderDto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(
		method = { RequestMethod.POST },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SaleOrderDto saveSaleOrder(@RequestBody SaleOrderDto saleOrderDto, HttpServletRequest request, HttpServletResponse response) {
		return saleOrderService.save(saleOrderDto);
	}

	/**
	 * This will return the Sale order.
	 * 
	 * @param saleOrderId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(
		value = { "/{saleOrderId}" },
		method = { RequestMethod.GET },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SaleOrderDto getSaleOrder(@PathVariable("saleOrderId") String saleOrderId, HttpServletRequest request, HttpServletResponse response) {
		return saleOrderService.get(saleOrderId);
	}

	/**
	 * This method will return all sale orders.
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(
		method = { RequestMethod.GET },
		consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<SaleOrderDto> getSaleOrder(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		return saleOrderService.findAll(pageNumber, pageSize);
	}

	/**
	 * This method will delete the sale order.
	 * 
	 * @param saleOrderId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(
		value = { "/{saleOrderId}" },
		method = { RequestMethod.DELETE })
	@ResponseBody
	public SaleOrderDto deleteSaleOrder(@PathVariable("saleOrderId") String saleOrderId, HttpServletRequest request, HttpServletResponse response) {
		return saleOrderService.delete(saleOrderId);
	}

}
