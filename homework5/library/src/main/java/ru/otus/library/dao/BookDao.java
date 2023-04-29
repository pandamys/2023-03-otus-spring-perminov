package ru.otus.library.dao;

import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Map;

public interface BookDao {
    Book getBookById(long id);

    List<Book> getAllBooks();

    void insertBook(Book book);

    void updateBook(long id, Map<String, Object> params);

    void deleteBookById(long id);

    Author getAuthorById(long id);

    Genre getGenreById(long id);
}
