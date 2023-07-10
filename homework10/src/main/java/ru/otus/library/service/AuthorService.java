package ru.otus.library.service;

import ru.otus.library.domain.Author;
import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);
}