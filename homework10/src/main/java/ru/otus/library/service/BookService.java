package ru.otus.library.service;

import ru.otus.library.domain.Book;
import java.util.List;

public interface BookService {
    Book getBookById(long id);

    Book getBookByName(String name);

    List<Book> getAllBooks();

    Book addBook(String name, Long author, Long genre);

    Book updateBook(long id, String name, long author, long genre);

    boolean removeBook(long id);
}
