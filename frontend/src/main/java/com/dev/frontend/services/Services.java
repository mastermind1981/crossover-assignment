package com.dev.frontend.services;

import java.util.ArrayList;
import java.util.List;

import com.dev.frontend.api.CustomerService;
import com.dev.frontend.api.ProductService;
import com.dev.frontend.api.SaleOrderService;
import com.dev.frontend.dto.CustomerDto;
import com.dev.frontend.dto.ProductDto;
import com.dev.frontend.dto.SaleOrderDto;
import com.dev.frontend.panels.ComboBoxItem;

public class Services {
	public static final int TYPE_PRODUCT = 1;
	public static final int TYPE_CUSTOMER = 2;
	public static final int TYPE_SALESORDER = 3;

	/*
	 * This method is called eventually after you click save on any edit screen
	 * object parameter is the return object from calling method guiToObject on
	 * edit screen and the type is identifier of the object type and may be
	 * TYPE_PRODUCT , TYPE_CUSTOMER or TYPE_SALESORDER
	 */
	public static Object save(Object object, int objectType) {
		if(object != null){
			if (TYPE_CUSTOMER == objectType) {
				return CustomerService.getCustomerService().saveCustomerDto((CustomerDto)object);
			} else if (TYPE_PRODUCT == objectType) {
				return ProductService.getProductService().saveProductDto((ProductDto)object);
			} else if (objectType == TYPE_SALESORDER) {
				return SaleOrderService.getSaleOrderService().saveSaleOrderDto((SaleOrderDto)object);
			}
		}
		return null;
	}

	public static Object readRecordByCode(String code, int objectType) {
		if (TYPE_CUSTOMER == objectType) {
			return CustomerService.getCustomerService().fetchCustomerDataByCode(code);
		} else if (TYPE_PRODUCT == objectType) {
			return ProductService.getProductService().fetchProductDataByCode(code);
		} else if (objectType == TYPE_SALESORDER) {
			return SaleOrderService.getSaleOrderService().fetchSaleOrderByCode(code);
		}
		return null;
	}

	/*
	 * This method is called when you click delete button on an edit view the
	 * code parameter is the code of (Customer - PRoduct ) or order number of
	 * Sales Order and the type is identifier of the object type and may be
	 * TYPE_PRODUCT , TYPE_CUSTOMER or TYPE_SALESORDER
	 */
	public static boolean deleteRecordByCode(String code, int objectType) {
		if (objectType == TYPE_CUSTOMER) {
			return CustomerService.getCustomerService().deleteCustomerDataByCode(code);
		} else if (objectType == TYPE_PRODUCT) {
			return ProductService.getProductService().deleteProductDataByCode(code);
		} else if (objectType == TYPE_SALESORDER) {
			return SaleOrderService.getSaleOrderService().deleteSaleOrderByCode(code);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static List<Object> listCurrentRecords(int objectType) {
		if (objectType == TYPE_CUSTOMER) {
			List<CustomerDto> customerDtoList = CustomerService.getCustomerService().fetchCustomerData();
			if (customerDtoList != null) {
				return (List<Object>) (Object) customerDtoList;
			}
		} else if (objectType == TYPE_PRODUCT) {
			List<ProductDto> productDtoList = ProductService.getProductService().fetchProductData();
			if (productDtoList != null) {
				return (List<Object>) (Object) productDtoList;
			}
		} else if (objectType == TYPE_SALESORDER) {
			List<SaleOrderDto> saleOrderDtos = SaleOrderService.getSaleOrderService().fetchSaleOrderData();
			if(saleOrderDtos != null){
				return (List<Object>) (Object) saleOrderDtos;
			}
		}
		return new ArrayList<Object>();
	}

	/*
	 * This method is called when a Combo Box need to be initialized and should
	 * return list of ComboBoxItem which contains code and description/name for
	 * all records of specified type
	 */
	public static List<ComboBoxItem> listCurrentRecordRefernces(int objectType) {
		List<ComboBoxItem> comboBoxItemList = new ArrayList<ComboBoxItem>();
		if (objectType == TYPE_CUSTOMER) {
			List<CustomerDto> customerDtoList = CustomerService.getCustomerService().fetchCustomerData();
			if (customerDtoList != null) {
				for (int i = 0; i < customerDtoList.size(); i++) {
					CustomerDto customerDto = customerDtoList.get(i);
					comboBoxItemList.add(new ComboBoxItem(customerDto.getCode(), customerDto.getName()));
				}
			}
		} else if (objectType == TYPE_PRODUCT) {
			List<ProductDto> productDtoList = ProductService.getProductService().fetchProductData();
			if (productDtoList != null) {
				for (int i = 0; i < productDtoList.size(); i++) {
					ProductDto productDto = productDtoList.get(i);
					comboBoxItemList.add(new ComboBoxItem(productDto.getCode(), productDto.getDescription()));
				}
			}
		}
		return comboBoxItemList;
	}

	/*
	 * This method is used to get unit price of product with the code passed as
	 * a parameter
	 */
	public static double getProductPrice(String productCode) {
		ProductDto productDto = ProductService.getProductService().fetchProductDataByCode(productCode);
		if (productDto != null) {
			return productDto.getPrice();
		}
		return 1;
	}
}
