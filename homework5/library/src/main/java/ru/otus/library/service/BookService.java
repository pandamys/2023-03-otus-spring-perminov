package ru.otus.library.service;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Map;

public interface BookService {
    Book getBookById(long id);

    Book getBookByName(String name);

    List<Book> getAllBooks();

    boolean addBook(String name, Long author, Long genre);

    void updateBook(long id, Map<String, Object> params);

    boolean removeBook(long id);
}
