package ru.otus.test.system.domain;

public class Answer {
    private final String textAnswer;

    private final boolean isCorrect;

    public Answer(String textAnswer,
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
