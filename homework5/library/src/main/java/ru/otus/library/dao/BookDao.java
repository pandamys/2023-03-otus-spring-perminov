package ru.otus.library.dao;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookDao {
    boolean addBook(String name,
                    String genre,
                    String authorName,
                    String authorSurname);

    boolean updateBookName(long id, String name);

    Book getBookById(long id);

    List<Book> getAllBooks();
}
