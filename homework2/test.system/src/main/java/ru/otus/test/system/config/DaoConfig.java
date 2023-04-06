package ru.otus.test.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.test.system.dao.TestDao;
import ru.otus.test.system.dao.TestDaoImpl;

@PropertySource("application.properties")
@Configuration
public class DaoConfig {
    @Bean
    public TestDao testDao(@Value("${csv.path}") String file,
                           @Value("${csv.delimiter.column}") String delimiterColumn,
                           @Value("${csv.delimiter.value}") String delimiterValue) {
        return new TestDaoImpl(file, delimiterColumn, delimiterValue);
    }
}
