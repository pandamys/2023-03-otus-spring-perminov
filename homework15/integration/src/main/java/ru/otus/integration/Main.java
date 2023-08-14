package ru.otus.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.integration.service.LifeChickenService;
import ru.otus.integration.service.TransformationService;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
        LifeChickenService lifeChickenService = ctx.getBean(LifeChickenService.class);
        lifeChickenService.generate();
    }
}
