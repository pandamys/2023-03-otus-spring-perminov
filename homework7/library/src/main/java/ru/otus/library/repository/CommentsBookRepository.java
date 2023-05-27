package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;

import java.util.List;

public interface CommentsBookRepository extends JpaRepository<CommentBook, Long> {
    @Query("select cb from CommentBook cb left join fetch cb.book where cb.book = :book")
    List<CommentBook> findByBook(@Param("book") Book book);
}
