package ru.otus.library.service;

import org.springframework.batch.core.launch.NoSuchJobExecutionException;

public interface JobService {
    void run() throws Exception;

    String showInfoAboutJob() throws NoSuchJobExecutionException;

    void restart() throws Exception;
}
