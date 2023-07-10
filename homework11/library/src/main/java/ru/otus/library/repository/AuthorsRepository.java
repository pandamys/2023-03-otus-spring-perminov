package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.domain.Author;

public interface AuthorsRepository extends ReactiveMongoRepository<Author, Long> {
}
