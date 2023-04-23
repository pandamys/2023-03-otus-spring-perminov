package ru.otus.test.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.test.system.service.TestService;

@SpringBootApplication
public class SpringBoot {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBoot.class, args);
        TestService testService = context.getBean(TestService.class);
        testService.testing();
    }
}
