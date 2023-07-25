package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Author;

public interface AuthorsRepository extends JpaRepository<Author, Long> {
}
