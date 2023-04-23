package ru.otus.test.system.service;

import ru.otus.test.system.domain.Person;
import ru.otus.test.system.domain.Question;

public interface TestService {
    void testing();

    void start(String s1, String s2);

    void printQuestions();

    void checkCorrectAnswer(Question question, int i);

    void stop();

    Person getUser();
}
