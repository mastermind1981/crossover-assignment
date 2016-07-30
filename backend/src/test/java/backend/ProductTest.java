package backend;

import static org.junit.Assert.assertNotNull;

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
import com.ecom.dto.ProductDto;
import com.ecom.exceptions.NotFoundException;
import com.ecom.model.Product;
import com.ecom.services.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	classes = ApplicationStarter.class,
	loader = SpringApplicationContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductTest {

	@Autowired
	private ProductService productService;

	/**
	 * This will test creation of product
	 */
	@Test
	public void firstCreateProduct() {
		Product product = new Product();

		product.setCode("PO123");
		product.setDescription("Beauty product");
		product.setPrice(1234.45d);
		product.setQuantity(20l);

		ProductDto productDto = productService.save(DtoConvertor.buildProductDto(product));
		assertNotNull(productDto);
	}

	/**
	 * This will test updation of the product
	 */
	@Test
	public void secondUpdateProduct() {
		Product product = new Product();
		product.setCode("PO123");
		product.setDescription("Beauty Saloon");
		product.setPrice(1234.45d);
		product.setQuantity(20l);

		ProductDto productDto = productService.update(DtoConvertor.buildProductDto(product));
		assertNotNull(productDto);
	}

	/**
	 * This will test get product
	 */
	@Test
	public void tGetProduct() {
		ProductDto productDto = productService.get("PO123");
		assertNotNull(productDto);
	}

	/**
	 * This will test deletion of the product.
	 */
	@Test(
		expected = NotFoundException.class)
	public void uDeleteProduct() {
		String code = "PO123";
		productService.delete(code);
		productService.get(code);
	}
}
