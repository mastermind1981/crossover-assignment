package backend.test;

import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.ecom.ApplicationStarter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;

@TestExecutionListeners({ DbUnitTestExecutionListener.class })
@ContextConfiguration(
	classes = ApplicationStarter.class,
	loader = SpringApplicationContextLoader.class)
@DirtiesContext
public abstract class AbstractRepository extends AbstractTransactionalJUnit4SpringContextTests {

}
