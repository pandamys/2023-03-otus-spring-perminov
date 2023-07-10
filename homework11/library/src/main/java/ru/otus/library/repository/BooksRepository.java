package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.library.domain.Book;

import java.util.List;

public interface BooksRepository extends ReactiveMongoRepository<Book, Long> {
    Book findByName(String name);

    @Override
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}
