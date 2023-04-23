package ru.otus.test.system.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String textQuestion;

    private List<Answer> answerList = new ArrayList<>();

    public Question(String textQuestion){
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

    public Answer getAnswer(int i) {
        if (answerList.size() > i && i >= 0){
            return answerList.get(i);
        }

        return null;
    }
}
