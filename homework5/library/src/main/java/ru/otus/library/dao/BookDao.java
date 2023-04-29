package ru.otus.library.dao;

import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;

public interface BookDao {
    boolean addBook(String name,
                    String genre,
                    String authorName,
                    String authorSurname);

    boolean updateBookName(long id, String name);

    Book getBookById(long id);

    List<Book> getAllBooks();

    Author getAuthorById(long id);

    Genre getGenreById(long id);
}
