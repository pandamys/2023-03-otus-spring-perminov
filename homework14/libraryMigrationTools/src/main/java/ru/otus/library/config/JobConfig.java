package ru.otus.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.library.domain.document.AuthorDoc;
import ru.otus.library.domain.document.GenreDoc;

@RequiredArgsConstructor
@Configuration
public class JobConfig {

    private final MongoTemplate mongoTemplate;

    @Bean
    public TaskletStep dropMongoDBStep(JobRepository jobRepository) {
        return new StepBuilder("dropMongoDB", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    mongoTemplate.getDb().drop();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public TaskletStep dropPreviousIdsStep(JobRepository jobRepository) {
        return new StepBuilder("dropPreviousIds", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    var query = new Query();
                    var update = new Update().unset("previousId");
                    mongoTemplate.findAndModify(query, update, GenreDoc.class);
                    mongoTemplate.findAndModify(query, update, AuthorDoc.class);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Job migrateToMongoJob(Step authorMigrationStep,
                                 Step genreMigrationStep,
                                 Step bookMigrationStep,
                                 JobRepository jobRepository) {
        return new JobBuilder("migrateToMongo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(dropMongoDBStep(jobRepository))
                .next(authorMigrationStep)
                .next(genreMigrationStep)
                .next(bookMigrationStep)
                .next(dropPreviousIdsStep(jobRepository))
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                    }
                })
                .build();
    }
}