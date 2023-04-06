package ru.otus.test.system.domain;

public class AnswerImpl implements Answer {
    private String textAnswer;

    private boolean isCorrect;

    public AnswerImpl(String textAnswer,
                      boolean isCorrect){
        this.textAnswer = textAnswer;
        this.isCorrect = isCorrect;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
