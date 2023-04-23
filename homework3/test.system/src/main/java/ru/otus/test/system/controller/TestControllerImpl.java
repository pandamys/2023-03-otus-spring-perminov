package ru.otus.test.system.controller;

import org.springframework.stereotype.Controller;
import ru.otus.test.system.domain.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class TestControllerImpl implements TestController {
    private Person person;

    @Override
    public String readConsole(String message){
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
    public int readIntConsole(String message){
        String parameter;
        int result = -1;
        parameter = readConsole(message);
        try {
            result = Integer.parseInt(parameter);
        } catch (NumberFormatException e){
            System.out.println("Вы ввели не число, ответ не засчитан");
        }
        return result;
    }

    @Override
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person){
        this.person = person;
    }
}
