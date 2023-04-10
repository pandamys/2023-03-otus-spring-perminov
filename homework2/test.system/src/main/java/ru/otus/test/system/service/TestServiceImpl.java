package ru.otus.test.system.service;

import org.springframework.stereotype.Service;
import ru.otus.test.system.controller.TestController;
import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.domain.Answer;
import ru.otus.test.system.domain.Person;
import ru.otus.test.system.domain.Question;
import ru.otus.test.system.domain.Test;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final TestDao dao;

    private final TestController controller;

    private Test test;

    public TestServiceImpl(TestDao dao,
                           TestController controller) {
        this.dao = dao;
        this.test = dao.get();
        this.controller = controller;
    }

    @Override
    public void testing(){
        start();
        printQuestions();
        stop();
    }

    @Override
    public void printQuestions(){
        if (test != null) {
            int x = 1;
            List<Question> questions = test.getQuestions();
            for (Question question : questions){
                System.out.printf("Question %d: %s%n", x, question.getTextQuestion());
                printAnswers(question);
                readUserAnswer(question);
                x++;
            }
        } else {
            throw new RuntimeException("Test not initialized");
        }
    }

    @Override
    public void checkCorrectAnswer(Question question, int userAnswer) {
        Answer answer;
        if (userAnswer - 1 < question.getAnswerList().size() && userAnswer > 0){
            answer = question.getAnswer(userAnswer - 1);
            if (answer != null && answer.isCorrect()){
                test.addCorrect();
            }
        }
    }

    @Override
    public void start() {
        String startMessage;
        String nameMessage;
        String surnameMessage;
        String name;
        String surname;

        startMessage = "Welcome to testing.";
        nameMessage = "Please enter your name";
        surnameMessage = "Please enter your surname";

        System.out.println(startMessage);
        name = controller.readConsole(nameMessage);
        surname = controller.readConsole(surnameMessage);
        controller.setPerson(new Person(name, surname));
    }

    @Override
    public void stop() {
        String stopMessage = String.format("Thank you %s for taking our test. Your result: %s",
                controller.getPerson(),
                test.getResults());
        System.out.println(stopMessage);
    }

    private void printAnswers(Question question) {
        List<Answer> answers;
        int y = 1;

        answers = question.getAnswerList();
        for (Answer answer : answers){
            System.out.printf("%d: %s%n", y, answer.getTextAnswer());
            y++;
        }
    }

    private void readUserAnswer(Question question) {
        String message = "Please enter number correct answer";
        int userAnswer = controller.readIntConsole(message);
        checkCorrectAnswer(question, userAnswer);
    }
}
