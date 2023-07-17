package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.domain.Book;

public interface BooksRepository extends ReactiveMongoRepository<Book, String> {
    Book findByName(String name);
}
