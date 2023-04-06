package ru.otus.test.system.domain;

import java.util.ArrayList;
import java.util.List;

public class QuestionImpl implements Question {
    private final String textQuestion;

    private List<Answer> answerList = new ArrayList<Answer>();

    public QuestionImpl(String textQuestion){
        this.textQuestion = textQuestion;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswers(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
