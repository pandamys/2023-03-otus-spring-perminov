package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorsRepository;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.CommentsBookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BooksRepository booksRepository;

    private final AuthorsRepository authorsRepository;

    private final GenreRepository genreRepository;

    private final CommentsBookRepository commentsBookRepository;


    public BookServiceImpl(BooksRepository booksRepository,
                           AuthorsRepository authorsRepository,
                           GenreRepository genreRepository,
                           CommentsBookRepository commentsBookRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.genreRepository = genreRepository;
        this.commentsBookRepository = commentsBookRepository;
    }

    @Override
    public Book getBookById(String id) {
        Optional<Book> optionalBook;
        Book book;
        List<CommentBook> commentBooks;

        optionalBook = booksRepository.findById(id);
        if (optionalBook.isPresent()){
            book = optionalBook.orElse(null);
            commentBooks = commentsBookRepository.findByBook(book);
            if (commentBooks.size() > 0) {
                book.setComments(commentBooks);
            } else {
                book.setComments(new ArrayList<>());
            }
            return book;
        } else {
            return null;
        }
    }

    @Override
    public Book getBookByName(String name) {
        Book book;
        List<CommentBook> commentBooks;

        book = booksRepository.findByName(name);
        if (book != null) {
            commentBooks = commentsBookRepository.findByBook(book);
            if (commentBooks.size() > 0) {
                book.setComments(commentBooks);
            } else {
                book.setComments(new ArrayList<>());
            }
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    public boolean addBook(String name,
                           String authorId,
                           String genreId) {
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
    public void updateBook(String id, String name, String authorId, String genreId) {
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
            if (authorId != null) {
                author = authorsRepository.findById(authorId);
                author.ifPresent(book::setAuthor);
            }
            if (genreId != null) {
                genre = genreRepository.findById(genreId);
                genre.ifPresent(book::setGenre);
            }
            booksRepository.save(book);
        }

    }

    @Override
    public boolean removeBook(String id) {
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
