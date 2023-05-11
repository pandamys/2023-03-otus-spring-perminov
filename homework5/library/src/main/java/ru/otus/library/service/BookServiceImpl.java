package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Map;

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
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public Book getBookByName(String name) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
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
            bookDao.insert(book);
            return true;
        }
    }

    @Override
    public void updateBook(long id, Map<String, Object> params) {
        Book book;
        Author author;
        Genre genre;
        book = bookDao.getById(id);
        if (params.containsKey("name")){
            book.setName((String) params.get("name"));
        }
        if (params.containsKey("authorId")){
            author = authorDao.getById((Long) params.get("authorId"));
            if (author != null){
                book.setAuthor(author);
            }
        }
        if (params.containsKey("genreId")){
            genre = genreDao.getById((Long) params.get("genreId"));
            if (genre != null){
                book.setGenre(genre);
            }
        }
        bookDao.update(book);
    }

    @Override
    public boolean removeBook(long id) {
        Book book;
        book = getBookById(id);
        if (book == null){
            return false;
        } else {
            bookDao.deleteById(id);
            return true;
        }
    }
}
