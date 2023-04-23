package ru.otus.test.system.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "csv")
public class CsvProps {
    private String path;

    private String delimiterColumn;

    private String delimiterValue;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDelimiterColumn() {
        return delimiterColumn;
    }

    public void setDelimiterColumn(String delimiterColumn) {
        this.delimiterColumn = delimiterColumn;
    }

    public String getDelimiterValue() {
        return delimiterValue;
    }

    public void setDelimiterValue(String delimiterValue) {
        this.delimiterValue = delimiterValue;
    }
}
