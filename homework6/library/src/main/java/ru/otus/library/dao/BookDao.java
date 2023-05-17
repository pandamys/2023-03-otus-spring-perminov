package ru.otus.library.dao;

import ru.otus.library.domain.Book;
import java.util.List;

public interface BookDao {
    Book getById(long id);

    List<Book> getAll();

    Book getByName(String name);

    Book insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
