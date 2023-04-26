package ru.otus.library.service;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookService {
    Book getBookById(long id);

    Book getBookByName(String name);

    List<Book> getAllBooks();

    boolean addBook(Book book);

    void updateBookName(long id, String name);

    boolean removeBook(long id);
}
