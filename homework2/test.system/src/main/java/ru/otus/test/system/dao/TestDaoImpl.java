package ru.otus.test.system.dao;

import ru.otus.test.system.domain.Test;
import ru.otus.test.system.domain.Question;
import ru.otus.test.system.domain.Answer;
import ru.otus.test.system.domain.TestImpl;
import ru.otus.test.system.domain.QuestionImpl;
import ru.otus.test.system.domain.AnswerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestDaoImpl implements TestDao {
    private final InputStream inputStream;

    private final String delimiterColumn;

    private final String delimiterCell;

    public TestDaoImpl(String nameFile,
                       String delimiterColumn,
                       String delimiterCell) {
        inputStream = getClass().getClassLoader().getResourceAsStream(nameFile);
        if (inputStream == null){
            throw new IllegalStateException("File not found: " + nameFile);
        }
        this.delimiterColumn = delimiterColumn;
        this.delimiterCell = delimiterCell;
    }

    @Override
    public Test get() {
        Test test;
        List<String> lines = new ArrayList<>();
        readFromCSV(lines);
        test = createTest(lines);

        return test;
    }

    private void readFromCSV(List<String> lines){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
            String newLine;

            while (reader.ready()){
                newLine = reader.readLine();
                lines.add(newLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Test createTest(List<String> lines){
        TestImpl test = new TestImpl();
        String[] splitLine;
        String line;
        Question question;
        List<Answer> answers;
        for (int i = 1; i < lines.size(); i++) {
            line = lines.get(i);
            splitLine = line.split(delimiterColumn);
            if (splitLine.length == 3){
                question = new QuestionImpl(splitLine[0]);
                answers = getAnswers(splitLine[1], splitLine[2]);
                question.setAnswers(answers);
                test.addQuestion(question);
            }
        }
        return test;
    }

    private List<Answer> getAnswers(String stringAnswers,
                                         String correct){
        String[] splitCell;
        Answer answer;
        List<Answer> answers = new ArrayList<>();

        splitCell = stringAnswers.split(delimiterCell);
        for (String stringAnswer: splitCell){
            answer = new AnswerImpl(stringAnswer, correct.equals(stringAnswer));
            answers.add(answer);
        }
        return answers;
    }
}