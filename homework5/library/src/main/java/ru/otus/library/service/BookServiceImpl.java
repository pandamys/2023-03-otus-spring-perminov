package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Map;

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
                           Long authorId,
                           Long genreId) {
        Book book;
        Author author;
        Genre genre;

        author = bookDao.getAuthorById(authorId);
        genre = bookDao.getGenreById(genreId);
        if (author == null || genre == null){
            return false;
        } else {
            book = new Book(name, author, genre);
            bookDao.insertBook(book);
            return true;
        }
    }

    @Override
    public void updateBook(long id, Map<String, Object> params) {
        Book book;
        Author author;
        Genre genre;
        book = bookDao.getBookById(id);
        if (params.containsKey("name")){
            book.setName((String) params.get("name"));
        }
        if (params.containsKey("authorId")){
            author = bookDao.getAuthorById((Long) params.get("authorId"));
            if (author != null){
                book.setAuthor(author);
            }
        }
        if (params.containsKey("genreId")){
            genre = bookDao.getGenreById((Long) params.get("genreId"));
            if (genre != null){
                book.setGenre(genre);
            }
        }
        bookDao.updateBook(book);
    }

    @Override
    public boolean removeBook(long id) {
        Book book;
        book = getBookById(id);
        if (book == null){
            return false;
        } else {
            bookDao.deleteBookById(id);
            return true;
        }
    }
}
