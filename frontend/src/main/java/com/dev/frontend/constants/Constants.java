package com.dev.frontend.constants;

import java.text.DecimalFormat;

public interface Constants {

	static String CUSTOMER_URI = "http://localhost:8888/backend/customer";
	static String PRODUCT_URI = "http://localhost:8888/backend/product";
	static String SALEORDER_URI = "http://localhost:8888/backend/saleorder";
	static DecimalFormat df = new DecimalFormat("#.##");

}
