package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;

import java.util.List;

public interface CommentsBookRepository extends MongoRepository<CommentBook, String> {
    List<CommentBook> findByBook(Book book);
}
