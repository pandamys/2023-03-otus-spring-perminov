package ru.otus.test.system.controller;

import ru.otus.test.system.domain.Person;

public interface TestController {

    String readConsole(String message);

    int readIntConsole(String message);

    Person getPerson();

    void setPerson(Person person);
}
