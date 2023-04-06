import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.service.TestService;

public class TestForTestService {
    private TestService testService;
    private TestDao testDao;
    @BeforeEach
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
            Assertions.fail("Excepted exception");
        } catch (RuntimeException e){
            Assertions.assertEquals(e.getMessage(), expectedMessage);
        }
    }
}
