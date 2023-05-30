package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorsRepository;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BooksRepository booksRepository;

    private final AuthorsRepository authorsRepository;

    private final GenreRepository genreRepository;


    public BookServiceImpl(BooksRepository booksRepository,
                           AuthorsRepository authorsRepository,
                           GenreRepository genreRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        Optional<Book> optionalBook;
        Book book;
        optionalBook = booksRepository.findById(id);
        if (optionalBook.isPresent()){
            book = optionalBook.orElse(null);
            book.getComments().size();
            return book;
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Book getBookByName(String name) {
        Book book;
        book = booksRepository.findByName(name);
        if (book != null) {
            book.getComments().size();
        }
        return book;
    }

    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    @Transactional
    public boolean addBook(String name,
                           Long authorId,
                           Long genreId) {
        Book book;
        Optional<Author> author;
        Optional<Genre> genre;

        author = authorsRepository.findById(authorId);
        genre = genreRepository.findById(genreId);
        if (author.isEmpty() || genre.isEmpty()) {
            return false;
        } else {
            book = new Book(name, author.get(), genre.get());
            booksRepository.save(book);
            return true;
        }
    }

    @Override
    @Transactional
    public void updateBook(long id, String name, long authorId, long genreId) {
        Optional<Book> bookOptional;
        Book book;
        Optional<Author> author;
        Optional<Genre> genre;
        bookOptional = booksRepository.findById(id);

        if (bookOptional.isPresent()) {
            book = bookOptional.get();
            if (name != null) {
                book.setName(name);
            }
            if (authorId > 0) {
                author = authorsRepository.findById(authorId);
                author.ifPresent(book::setAuthor);
            }
            if (genreId > 0) {
                genre = genreRepository.findById(genreId);
                genre.ifPresent(book::setGenre);
            }
            booksRepository.save(book);
        }

    }

    @Override
    @Transactional
    public boolean removeBook(long id) {
        Book book;
        book = getBookById(id);
        if (book == null){
            return false;
        } else {
            booksRepository.delete(book);
            return true;
        }
    }
}
