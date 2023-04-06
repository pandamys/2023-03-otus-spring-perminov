import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.service.TestService;

public class TestForTestService {
    private TestService testService;
    private TestDao testDao;
    @Before
    public void setUp() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        testDao = context.getBean(TestDao.class);
        testService = context.getBean(TestService.class);
    }

    @Test
    public void exception_Initialize_Test() throws RuntimeException {
        String expectedMessage = "Test not initialized";
        try {
            testService.printQuestions();
            Assert.fail("Excepted exception");
        } catch (RuntimeException e){
            Assert.assertEquals(e.getMessage(), expectedMessage);
        }
    }
}
