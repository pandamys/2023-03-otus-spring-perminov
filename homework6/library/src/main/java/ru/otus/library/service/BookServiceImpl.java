package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;


    public BookServiceImpl(BookDao bookDao,
                           AuthorDao authorDao,
                           GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookByName(String name) {
        return bookDao.getByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    @Transactional
    public boolean addBook(String name,
                           Long authorId,
                           Long genreId) {
        Book book;
        Author author;
        Genre genre;

        author = authorDao.getById(authorId);
        genre = genreDao.getById(genreId);
        if (author == null || genre == null){
            return false;
        } else {
            book = new Book(name, author, genre);
            bookDao.save(book);
            return true;
        }
    }

    @Override
    @Transactional
    public void updateBook(long id, String name, long authorId, long genreId) {
        Book book;
        Author author;
        Genre genre;
        book = bookDao.getById(id);
        if (name != null){
            book.setName(name);
        }
        if (authorId > 0){
            author = authorDao.getById(authorId);
            if (author != null){
                book.setAuthor(author);
            }
        }
        if (genreId > 0){
            genre = genreDao.getById(genreId);
            if (genre != null){
                book.setGenre(genre);
            }
        }
        bookDao.save(book);
    }

    @Override
    @Transactional
    public boolean removeBook(long id) {
        Book book;
        book = getBookById(id);
        if (book == null){
            return false;
        } else {
            bookDao.remove(book);
            return true;
        }
    }
}
