import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import ru.otus.test.system.controller.TestController;
import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.domain.Answer;
import ru.otus.test.system.domain.Person;
import ru.otus.test.system.domain.Question;
import ru.otus.test.system.service.TestService;
import ru.otus.test.system.service.TestServiceImpl;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class TestForTestService {
    private final PrintStream standardOut = System.out;
    private TestService testService;
    private TestController testControllerMock;
    private TestDao testDaoMock;
    @BeforeEach
    public void setUp() {
        testDaoMock = Mockito.mock(TestDao.class);
        testControllerMock = Mockito.mock(TestController.class);
        Mockito.when(testDaoMock.get()).thenReturn(createTestMock());
        testService = new TestServiceImpl(testDaoMock, testControllerMock);
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

        Mockito.when(testControllerMock.readConsole("Please enter your name")).thenReturn("V");
        Mockito.when(testControllerMock.readConsole("Please enter your surname")).thenReturn("P");
        Mockito.when(testControllerMock.readIntConsole("Please enter number correct answer")).thenReturn(2);
        Mockito.when(testControllerMock.getPerson()).thenReturn(new Person("V", "P"));

        testService.testing();
        Assertions.assertEquals(expectedMessage, outputStreamCaptor.toString().trim());
    }

    private ru.otus.test.system.domain.Test createTestMock(){
        ru.otus.test.system.domain.Test test = new ru.otus.test.system.domain.Test();
        Question question = new Question("Question1");
        ArrayList<Answer> answers = new ArrayList<Answer>(){
            {
                add(new Answer("answer1", false));
                add(new Answer("answer2", true));
                add(new Answer("answer3", false));
            }
        };
        question.setAnswers(answers);
        test.addQuestion(question);
        return test;
    }
}
