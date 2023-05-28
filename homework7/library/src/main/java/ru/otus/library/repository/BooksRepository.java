package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<Book, Long> {
    @Override
    @Query("select b from Book b join fetch b.comments where b.id = :id")
    Optional<Book> findById(@Param("id") Long aLong);

    @Query("select b from Book b join fetch b.comments where b.name = :name")
    Book findByName(@Param("name") String name);
}
