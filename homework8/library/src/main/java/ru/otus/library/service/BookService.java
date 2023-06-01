package ru.otus.library.service;

import ru.otus.library.domain.Book;
import java.util.List;

public interface BookService {
    Book getBookById(String id);

    Book getBookByName(String name);

    List<Book> getAllBooks();

    boolean addBook(String name, String author, String genre);

    void updateBook(String id, String name, String author, String genre);

    boolean removeBook(String id);
}
