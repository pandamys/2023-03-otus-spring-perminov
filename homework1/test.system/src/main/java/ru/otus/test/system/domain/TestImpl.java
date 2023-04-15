package ru.otus.test.system.domain;

import java.util.ArrayList;
import java.util.List;

public class TestImpl implements Test {
    private List<Question> questions = new ArrayList<>();

    private int correctAnswers = 0;

    public TestImpl() {
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Answer getAnswer(int question, int answer) {
        return questions.get(question).getAnswerList().get(answer);
    }

    @Override
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public int getResults() {
        return correctAnswers;
    }


}
