package ru.otus.test.system.service;

import ru.otus.test.system.domain.Question;

public interface TestService {
    void testing();

    void start();

    void printQuestions();

    void checkCorrectAnswer(Question question, int i);

    void stop();
}
