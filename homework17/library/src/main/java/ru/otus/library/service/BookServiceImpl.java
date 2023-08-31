package ru.otus.library.service;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private CircuitBreakerFactory cbFactory;

    public BookServiceImpl(BooksRepository booksRepository,
                           AuthorsRepository authorsRepository,
                           GenreRepository genreRepository,
                           CircuitBreakerFactory cbFactory) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.genreRepository = genreRepository;
        this.cbFactory = cbFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return cbFactory.create("bookById").run(
                () -> {
                    Optional<Book> optionalBook;
                    Book book;
                    optionalBook = booksRepository.findById(id);
                    if (optionalBook.isPresent()) {
                        book = optionalBook.orElse(null);
                        book.getComments().size();
                        return book;
                    } else {
                        return null;
                    }
                },
                throwable -> fallbackUndefinedBook());
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookByName(String name) {
        return cbFactory.create("bookByName").run(
                () -> {
                    Book book;
                    book = booksRepository.findByName(name);
                    if (book != null) {
                        book.getComments().size();
                    }
                    return book;
                },
                throwable -> fallbackUndefinedBook());
    }

    public List<Book> getAllBooks() {
        return cbFactory.create("allBooks").run(
                () -> booksRepository.findAll(),
                throwable -> fallbackUndefinedBooksList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public boolean removeBook(long id) {
        Book book;
        book = getBookById(id);
        if (book == null) {
            return false;
        } else {
            booksRepository.delete(book);
            return true;
        }
    }

    private List<Book> fallbackUndefinedBooksList() {
        return List.of(getEmptyBook());
    }

    private Book fallbackUndefinedBook() {
        return getEmptyBook();
    }

    private Book getEmptyBook() {
        return new Book(0L, "N/A", new Author("N/A", "N/A"), new Genre("N/A"));
    }
}
