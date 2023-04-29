package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getBookById(id);
    }

    @Override
    public Book getBookByName(String name) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public boolean addBook(String name,
                           String authorName,
                           String authorSurname,
                           String genre) {
        return false;
    }

    @Override
    public void updateBookName(long id, String name) {

    }

    @Override
    public boolean removeBook(long id) {
        return false;
    }
}
