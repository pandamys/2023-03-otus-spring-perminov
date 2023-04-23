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

    private final LocalizationService localizationService;

    public TestServiceImpl(TestDao dao,
                           TestController controller,
                           LocalizationService localizationService) {
        this.dao = dao;
        this.test = dao.get();
        this.controller = controller;
        this.localizationService = localizationService;
    }

    @Override
    public void testing(){
        test.resetResults();
        printQuestions();
        stop();
    }

    @Override
    public void start(String name, String surname) {
        String startMessage;

        controller.setPerson(new Person(name, surname));
        startMessage = localizationService
                .getLocalizationMessage("message.welcome", controller.getPerson().getFullName());
        System.out.println(startMessage);
    }

    @Override
    public void stop() {
        String stopMessage = localizationService
                .getLocalizationMessage(
                        "message.stop",
                        controller.getPerson().getFullName(), test.getResults());
        System.out.println(stopMessage);
    }

    @Override
    public void printQuestions(){
        if (test != null) {
            int x = 1;
            List<Question> questions = test.getQuestions();
            for (Question question : questions){
                String questionText = localizationService.getLocalizationMessage("message.question");
                System.out.printf("%s %d: %s%n", questionText, x, question.getTextQuestion());
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
    public Person getUser(){
        return controller.getPerson();
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
        String message = localizationService.getLocalizationMessage("message.answer", null);
        int userAnswer = controller.readIntConsole(message);
        checkCorrectAnswer(question, userAnswer);
    }
}
