package ru.otus.test.system.controller;

import ru.otus.test.system.domain.Person;

public interface TestController {

    String readParameter(String message);

    int readIntParameter(String message);

    Person getPerson();

    void setPerson(Person person);
}
