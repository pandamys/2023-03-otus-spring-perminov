package ru.otus.test.system.service;

import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.domain.Answer;
import ru.otus.test.system.domain.Question;
import ru.otus.test.system.domain.Test;
import java.util.List;

public class TestServiceImpl implements TestService {
    private final TestDao dao;

    private Test test;

    public TestServiceImpl(TestDao dao) {
        this.dao = dao;
        getTest();
    }

    @Override
    public void getTest() {
        this.test = dao.get();
    }

    @Override
    public void printQuestions(){
        if (test != null) {
            int x = 1;
            List<Question> questions = test.getQuestions();
            for (Question question : questions){
                System.out.printf("Question %d: %s%n", x, question.getTextQuestion());
                printAnswers(question);
                x++;
            }
        } else {
            throw new RuntimeException("Test not initialized");
        }
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
}
