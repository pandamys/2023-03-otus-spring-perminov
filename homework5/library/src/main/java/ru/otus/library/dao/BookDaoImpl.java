package ru.otus.library.dao;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Override
    public boolean addBook(String name, String genre, String authorName, String authorSurname) {
        return false;
    }

    @Override
    public boolean updateBookName(long id, String name) {
        return false;
    }

    @Override
    public Book getBookById(long id) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }
}
