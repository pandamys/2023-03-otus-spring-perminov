package ru.otus.library.service;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.CommentsBookRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentsBookRepository commentsBookRepository;

    private final BooksRepository booksRepository;

    private final CircuitBreakerFactory cbFactory;

    public CommentServiceImpl(CommentsBookRepository commentsBookRepository,
                              BooksRepository booksRepository,
                              CircuitBreakerFactory cbFactory) {
        this.commentsBookRepository = commentsBookRepository;
        this.booksRepository = booksRepository;
        this.cbFactory = cbFactory;
    }

    @Override
    public CommentBook getById(long id) {
        return cbFactory.create("commentById").run(
                () -> {
                    Optional<CommentBook> comment;
                    comment = commentsBookRepository.findById(id);
                    return comment.orElse(null);
                },
                throwable -> fallbackUndefinedComment());

    }

    @Override
    public List<CommentBook> getCommentsForBook(long bookId) {
        return cbFactory.create("commentsForBook").run(
                () -> {
                    Optional<Book> book;
                    book = booksRepository.findById(bookId);
                    if (book.isEmpty()) {
                        return Collections.emptyList();
                    }
                    return commentsBookRepository.findByBook(book.get());
                },
                throwable -> fallbackUndefinedCommentsList());
    }

    public List<CommentBook> getAll() {
        return cbFactory.create("allComments").run(
                () -> commentsBookRepository.findAll(),
                throwable -> fallbackUndefinedCommentsList());
    }

    @Override
    public boolean addComment(String text, long bookId) {
        Optional<Book> book;
        CommentBook commentBook;
        book = booksRepository.findById(bookId);
        if (book.isPresent()) {
            commentBook = new CommentBook(text, book.get());
            commentsBookRepository.save(commentBook);
            return true;
        }
        return false;
    }

    @Override
    public void updateComment(long id, String text, long bookId) {
        Optional<CommentBook> optionalCommentBook;
        CommentBook commentBook;
        Optional<Book> book;
        optionalCommentBook = commentsBookRepository.findById(id);
        if (optionalCommentBook.isPresent()) {
            commentBook = optionalCommentBook.get();
            if (text != null && !text.equals("")) {
                commentBook.setText(text);
            }
            if (bookId > 0) {
                book = booksRepository.findById(bookId);
                book.ifPresent(commentBook::setBook);
            }
            commentsBookRepository.save(commentBook);
        }
    }

    @Override
    public boolean removeComment(long id) {
        Optional<CommentBook> commentBook;
        commentBook = commentsBookRepository.findById(id);
        commentBook.ifPresent(commentsBookRepository::delete);
        return true;
    }

    private List<CommentBook> fallbackUndefinedCommentsList() {
        return List.of(getEmptyCommentBook());
    }

    private CommentBook fallbackUndefinedComment() {
        return getEmptyCommentBook();
    }

    private CommentBook getEmptyCommentBook() {
        return new CommentBook("N/A", new Book());
    }
}
