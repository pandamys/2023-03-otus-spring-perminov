package ru.otus.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.library.service.JobService;

@ShellComponent
public class ShellCommands {
    private final JobService jobService;

    public ShellCommands(JobService jobService) {
        this.jobService = jobService;
    }

    @ShellMethod(value = "Запуск миграции", key = "run")
    @ShellMethodAvailability(value = "isAvailableToRun")
    public void startJob() throws Exception {
        jobService.run();
    }

    @ShellMethod(value = "Перезапуск миграции", key = "restart")
    @ShellMethodAvailability(value = "isAvailableToRestart")
    public void restartJob() throws Exception {
        jobService.restart();
    }

    @ShellMethod(value = "Показать информацию о задаче", key = "info")
    @ShellMethodAvailability(value = "isAvailableToRerun")
    public String showJobInfo() throws Exception {
        return jobService.showInfoAboutJob();
    }
}
