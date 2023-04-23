package ru.otus.test.system.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AppProps.class, CsvProps.class})
public class ApplicationConfig {
}
