package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<Book, Long> {
    @Override
    @EntityGraph(value = "book", attributePaths = {"author", "genre", "comments"})
    List<Book> findAll();

    @Override
    @EntityGraph(value = "book", attributePaths = {"author", "genre", "comments"})
    Optional<Book> findById(Long aLong);

    Book findByName(String name);
}
