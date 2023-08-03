package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.stereotype.Service;

import java.util.Properties;

@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {
    private final JobOperator jobOperator;
    private Long executionId;

    @Override
    public void run() throws Exception {
        if (executionId != null) {
            throw new IllegalArgumentException("Задача уже запущена");
        }
        executionId = jobOperator.start("migrateToMongo", new Properties());
    }

    @Override
    public String showInfoAboutJob() throws NoSuchJobExecutionException {
        return jobOperator.getSummary(executionId);
    }

    @Override
    public void restart() throws Exception {
        if (executionId == null) {
            throw new IllegalArgumentException("Задача ещё не запущена");
        }
        jobOperator.restart(executionId);
    }
}
