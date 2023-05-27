package ru.otus.library.dao;

import ru.otus.library.domain.Book;
import java.util.List;

public interface BookDao {
    Book getById(long id);

    List<Book> getAll();

    Book getByName(String name);

    Book save(Book book);

    void remove(Book book);
}
