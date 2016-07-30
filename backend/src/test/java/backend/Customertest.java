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
import com.ecom.dto.CustomerDto;
import com.ecom.exceptions.NotFoundException;
import com.ecom.services.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	classes = ApplicationStarter.class,
	loader = SpringApplicationContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Customertest {

	@Autowired
	private CustomerService customerService;

	/**
	 * This will create / update customer
	 */
	@Test
	public void firstCreateCustomer() {
		CustomerDto customerDto = new CustomerDto("CUS002", "Andre Mark", "10 Anenue Street, California", "124000023232", "124000023232", 12345d);
		CustomerDto savedCustomerDto = customerService.save(customerDto);
		assertNotNull(savedCustomerDto);
	}

	/**
	 * This will update customer
	 */
	@Test
	public void secondUpdateCustomer() {
		CustomerDto customerDto = new CustomerDto("CUS002", "Andre Henry", "10 Anenue Street, California", "124000023232", "124000023232", 12345d);
		CustomerDto savedCustomerDto = customerService.update(customerDto);
		assertNotNull(savedCustomerDto);
	}

	@Test
	public void tecondGetCustomer() {
		CustomerDto savedCustomerDto = customerService.get("CUS002");
		assertNotNull(savedCustomerDto);
	}

	/**
	 * This will delete customer.
	 */
	@Test(
		expected = NotFoundException.class)
	public void uDeleteCustomer() {
		String code = "CUS002";
		customerService.delete(code);
		customerService.get(code);
	}

}
