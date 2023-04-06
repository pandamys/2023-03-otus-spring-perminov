package ru.otus.test.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.test.system.controller.TestController;
import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.service.TestService;
import ru.otus.test.system.service.TestServiceImpl;

@Configuration
public class ServiceConfig {
    @Bean
    public TestService getTestService(TestDao dao, TestController controller) {
        return new TestServiceImpl(dao, controller);
    }
}
