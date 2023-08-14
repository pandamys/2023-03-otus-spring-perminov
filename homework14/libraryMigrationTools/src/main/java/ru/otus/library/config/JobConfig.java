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
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.library.domain.document.AuthorDoc;
import ru.otus.library.domain.document.GenreDoc;

@RequiredArgsConstructor
@Configuration
public class JobConfig {

    private final MongoTemplate mongoTemplate;
    private final MongoTransactionManager mongoTransactionManager;

    @Bean
    public TaskletStep dropPreviousIdsStep(JobRepository jobRepository) {
        return new StepBuilder("dropPreviousIds", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    var query = new Query(Criteria.where("previousId").gt(0));
                    var update = new Update().unset("previousId");
                    mongoTemplate.updateMulti(query, update, AuthorDoc.class);
                    mongoTemplate.updateMulti(query, update, GenreDoc.class);
                    return RepeatStatus.FINISHED;
                }).transactionManager(mongoTransactionManager)
                .build();
    }

    @Bean
    public Job migrateToMongoJob(Step authorMigrationStep,
                                 Step genreMigrationStep,
                                 Step bookMigrationStep,
                                 JobRepository jobRepository) {
        return new JobBuilder("migrateToMongo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(authorMigrationStep)
                .next(genreMigrationStep)
                .next(bookMigrationStep)
                .next(dropPreviousIdsStep(jobRepository))
                .build();
    }
}