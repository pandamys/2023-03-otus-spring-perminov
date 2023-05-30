package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;

import java.util.List;

public interface CommentsBookRepository extends JpaRepository<CommentBook, Long> {
    List<CommentBook> findByBook(Book book);
}
