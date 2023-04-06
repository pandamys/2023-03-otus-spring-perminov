package ru.otus.test.system.controller;

import ru.otus.test.system.domain.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestControllerImpl implements TestController {
    private Person person;

    @Override
    public void startTesting() {
        String startMessage;
        String nameMessage;
        String surnameMessage;
        String name;
        String surname;

        startMessage = "Welcome to testing.";
        nameMessage = "Please enter your name";
        surnameMessage = "Please enter your surname";

        System.out.println(startMessage);
        name = readParameter(nameMessage);
        surname = readParameter(surnameMessage);
        this.person = new Person(name, surname);
    }

    @Override
    public String readParameter(String message){
        String parameter;
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(reader);

        System.out.println(message);
        try {
            parameter = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error reading parameter");
        }
        return parameter;
    }

    @Override
    public int readIntParameter(String message){
        String parameter;
        int result = -1;
        parameter = readParameter(message);
        try {
            result = Integer.parseInt(parameter);
        } catch (NumberFormatException e){
            System.out.println("Вы ввели не число, ответ не засчитан");
        }
        return result;
    }

    public Person getPerson() {
        return person;
    }
}
