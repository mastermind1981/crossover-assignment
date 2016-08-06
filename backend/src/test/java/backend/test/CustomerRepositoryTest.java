package backend.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecom.model.Customer;
import com.ecom.repository.CustomerRepository;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@DatabaseSetup(CustomerRepositoryTest.DATASET)
@DatabaseTearDown(
	type = DatabaseOperation.DELETE_ALL,
	value = { CustomerRepositoryTest.DATASET })
public class CustomerRepositoryTest extends AbstractRepository {

	protected static final String DATASET = "classpath:customer.xml";

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void testFindAll() {
		List<Customer> got = (List<Customer>) customerRepository.findAll();
		assertEquals(3, got.size());
	}

}
