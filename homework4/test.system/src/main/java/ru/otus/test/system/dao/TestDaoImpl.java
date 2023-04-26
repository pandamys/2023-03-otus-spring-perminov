package ru.otus.test.system.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.test.system.configs.AppProps;
import ru.otus.test.system.domain.Test;
import ru.otus.test.system.domain.Question;
import ru.otus.test.system.domain.Answer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestDaoImpl implements TestDao {
    private final InputStream inputStream;

    private final String delimiterColumn;

    private final String delimiterCell;

    private final AppProps appProps;

    public TestDaoImpl(@Value("${csv.path}") String nameFile,
                       @Value("${csv.delimiter.column}") String delimiterColumn,
                       @Value("${csv.delimiter.value}") String delimiterCell,
                       AppProps appProps) {
        this.appProps = appProps;
        nameFile = nameFile + appProps.getLocale() + ".csv";
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
            while (reader.ready()){
                lines.add(reader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Test createTest(List<String> lines){
        Test test = new Test();
        String[] splitLine;
        String line;
        Question question;
        List<Answer> answers;
        for (int i = 1; i < lines.size(); i++) {
            line = lines.get(i);
            splitLine = line.split(delimiterColumn);
            if (splitLine.length == 3){
                question = new Question(splitLine[0]);
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
            answer = new Answer(stringAnswer, correct.equals(stringAnswer));
            answers.add(answer);
        }
        return answers;
    }
}