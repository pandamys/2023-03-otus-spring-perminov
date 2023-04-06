package ru.otus.test.system;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.test.system.service.TestService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestService testService = context.getBean(TestService.class);
        testService.getTest();
        testService.printQuestions();
    }
}
