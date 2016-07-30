package backend;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ecom.ApplicationStarter;
import com.ecom.convertors.DtoConvertor;
import com.ecom.dto.CustomerDto;
import com.ecom.dto.OrderLinesDto;
import com.ecom.dto.ProductDto;
import com.ecom.dto.SaleOrderDto;
import com.ecom.exceptions.DataIntegrityException;
import com.ecom.exceptions.NotFoundException;
import com.ecom.model.Product;
import com.ecom.services.CustomerService;
import com.ecom.services.ProductService;
import com.ecom.services.SaleOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	classes = ApplicationStarter.class,
	loader = SpringApplicationContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SaleOrderTest {

	@Autowired
	SaleOrderService saleOrderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	/**
	 * This will create sale order
	 */
	@Test
	public void firstCreateSaleOrder() {

		CustomerDto customerDto = new CustomerDto("CUS001", "Andre Mark", "10 Anenue Street, California", "124000023232", "124000023232", 12345d);
		CustomerDto savedCustomerDto = customerService.save(customerDto);

		Product product = new Product();
		product.setCode("PO124");
		product.setDescription("Beauty product");
		product.setPrice(1234.45d);
		product.setQuantity(20l);
		ProductDto productDto = productService.save(DtoConvertor.buildProductDto(product));

		SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto.setCustomer(new CustomerDto(savedCustomerDto.getCode()));
		saleOrderDto.setTotalPrice(Double.parseDouble(10 * product.getPrice() + ""));
		saleOrderDto.setOrderNumber("ON001");
		saleOrderDto.getOrderLines().add(new OrderLinesDto(new ProductDto(productDto.getCode()), 10l));

		saleOrderDto = saleOrderService.save(saleOrderDto);
		assertNotNull(saleOrderDto);
	}

	@Test(
		expected = DataIntegrityException.class)
	public void gDeleteProduct() {
		productService.delete("PO124");
	}

	@Test(
		expected = DataIntegrityException.class)
	public void hDeleteCustomer() {
		customerService.delete("CUS001");
	}

	/**
	 * This will test get sale order.
	 */
	@Test
	public void secondGetSaleOrder() {
		SaleOrderDto saleOrderDto = saleOrderService.get("ON001");
		assertNotNull(saleOrderDto);
	}
	
	/**
	 * This will test deletion of sale order.
	 */
	@Test(expected=NotFoundException.class)
	public void tDeleteSaleOrder() {
		String saleOrderCode = "ON001";
		saleOrderService.delete(saleOrderCode);
		saleOrderService.get(saleOrderCode);
	}

	/**
	 * This will test deletion of product.
	 */
	@Test(expected=NotFoundException.class)
	public void udeleteProduct() {
		String productCode = "PO124";
		productService.delete(productCode);
		productService.get(productCode);
	}

	/**
	 * This will test deletion of sale order.
	 */
	@Test(expected=NotFoundException.class)
	public void vdeleteCustomer() {
		String customerCode = "CUS001";
		customerService.delete(customerCode);
		customerService.get(customerCode);
	}
	
}
