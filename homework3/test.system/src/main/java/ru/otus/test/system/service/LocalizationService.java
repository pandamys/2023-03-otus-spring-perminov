package ru.otus.test.system.service;

public interface LocalizationService {
    String getLocalizationMessage(String key, Object... args);
}
