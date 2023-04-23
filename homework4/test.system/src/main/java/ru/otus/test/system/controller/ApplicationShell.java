package ru.otus.test.system.controller;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.test.system.configs.AppProps;
import ru.otus.test.system.service.TestService;

import java.util.Locale;

@ShellComponent
public class ApplicationShell {
    private final TestService testService;

    private final AppProps appProps;

    public ApplicationShell(TestService testService,
                            AppProps appProps){
        this.testService = testService;
        this.appProps = appProps;
    }

    @ShellMethod(value = "Start testing", key = {"s", "start", "run"})
    @ShellMethodAvailability(value = "isUserSet")
    public void startTesting(){
        testService.testing();
    }

    @ShellMethod(value = "Change locale", key = {"t"})
    public void changeLocale(Locale locale){
        appProps.setLocale(locale);
    }

    @ShellMethod(value = "Set user Name and Surname", key = {"n", "name"})
    public void setUser(String name, String surname){
        testService.start(name, surname);
    }

    private Availability isUserSet(){
        return testService.getUser() == null ? Availability.unavailable("\"Please first enter command 'name'\"")
                : Availability.available();
    }
}
