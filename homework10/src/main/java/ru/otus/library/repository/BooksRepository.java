package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Book;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Long> {
    Book findByName(String name);

    @Override
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}
