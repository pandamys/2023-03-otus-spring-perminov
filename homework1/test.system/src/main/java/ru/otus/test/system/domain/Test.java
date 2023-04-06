package ru.otus.test.system.domain;

import java.util.List;

public interface Test {
    List<Question> getQuestions();

    void addQuestion(Question question);

    Answer getAnswer(int question, int answer);

    int getResults();
}
