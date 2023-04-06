package ru.otus.test.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.test.system.controller.TestController;
import ru.otus.test.system.controller.TestControllerImpl;

@Configuration
public class ControllerConfig {
    @Bean
    public TestController testController(){
        return new TestControllerImpl();
    }
}
