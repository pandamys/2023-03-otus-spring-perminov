package ru.otus.test.system.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.test.system.configs.AppProps;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;

    private final AppProps appProps;

    public LocalizationServiceImpl(MessageSource messageSource, AppProps appProps){
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    @Override
    public String getLocalizationMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, appProps.getLocale());
    }
}
