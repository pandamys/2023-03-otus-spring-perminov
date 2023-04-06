package ru.otus.test.system.domain;

import java.util.List;

public interface Question {
    String getTextQuestion();

    List<Answer> getAnswerList();

    void setAnswers(List<Answer> answerList);
}
