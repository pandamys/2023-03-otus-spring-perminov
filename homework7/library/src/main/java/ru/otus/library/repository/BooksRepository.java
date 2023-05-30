package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.library.domain.Book;

public interface BooksRepository extends JpaRepository<Book, Long> {
    Book findByName(String name);
}
