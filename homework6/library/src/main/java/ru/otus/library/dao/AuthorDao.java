package ru.otus.library.dao;

import ru.otus.library.domain.Author;

public interface AuthorDao {
    Author getById(long id);
}
