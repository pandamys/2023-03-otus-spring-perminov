package ru.otus.library.config.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.library.domain.document.BookDoc;
import ru.otus.library.domain.entity.Book;
import jakarta.persistence.EntityManagerFactory;
import ru.otus.library.dto.EntityMapper;

@RequiredArgsConstructor
@Configuration
public class BookMigrationStepConfig {
    private final EntityManagerFactory managerFactory;
    private final MongoTemplate mongoTemplate;
    private final EntityMapper mapper;
    private final PlatformTransactionManager transactionManager;

    @StepScope
    @Bean
    public JpaPagingItemReader<Book> bookReader() {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookReader")
                .entityManagerFactory(managerFactory)
                .queryString("select b from Book b")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookDoc> bookProcessor() {
        return mapper::toBookDocument;
    }

    @StepScope
    @Bean
    public MongoItemWriter<BookDoc> bookWriter() {
        return new MongoItemWriterBuilder<BookDoc>()
                .collection("books")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step bookMigrationStep(JpaPagingItemReader<Book> bookReader,
                                  ItemProcessor<Book, BookDoc> bookProcessor,
                                  MongoItemWriter<BookDoc> bookWriter,
                                  JobRepository jobRepository) {
        return new StepBuilder("migrateBook", jobRepository)
                .<Book, BookDoc>chunk(3, transactionManager)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .allowStartIfComplete(Boolean.TRUE)
                .build();
    }
}
