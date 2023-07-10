package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;

public interface CommentsBookRepository extends ReactiveMongoRepository<CommentBook, Long> {
    Flux<CommentBook> findByBook(Book book);
}
