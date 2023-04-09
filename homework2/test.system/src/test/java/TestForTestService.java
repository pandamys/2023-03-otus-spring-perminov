import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import ru.otus.test.system.Main;
import ru.otus.test.system.controller.TestController;
import ru.otus.test.system.controller.TestControllerImpl;
import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.dao.TestDaoImpl;
import ru.otus.test.system.domain.Person;
import ru.otus.test.system.service.TestService;
import ru.otus.test.system.service.TestServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestForTestService {
    private final PrintStream standardOut = System.out;
    private TestService testService;
    private TestController testControllerMock;
    @BeforeEach
    public void setUp() {
        TestDao testDaoTest = new TestDaoImpl("testQuestions.csv", ";", "#");
        testControllerMock = Mockito.mock(TestController.class);
        testService = new TestServiceImpl(testDaoTest, testControllerMock);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void integration_test() {
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        String expectedMessage = "Welcome to testing.\r\n" +
                "Question 1: Question1\r\n" +
                "1: answer1\r\n" +
                "2: answer2\r\n" +
                "3: answer3\r\n" +
                "Thank you V P for taking our test. Your result: 1";

        Mockito.when(testControllerMock.readParameter("Please enter your name")).thenReturn("V");
        Mockito.when(testControllerMock.readParameter("Please enter your surname")).thenReturn("P");
        Mockito.when(testControllerMock.readIntParameter("Please enter number correct answer")).thenReturn(2);
        Mockito.when(testControllerMock.getPerson()).thenReturn(new Person("V", "P"));

        testService.start();
        testService.printQuestions();
        testService.stop();
        Assertions.assertEquals(expectedMessage, outputStreamCaptor.toString().trim());
    }
}
