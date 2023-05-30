package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public CommentServiceImpl(CommentsBookRepository commentsBookRepository,
                              BooksRepository booksRepository) {
        this.commentsBookRepository = commentsBookRepository;
        this.booksRepository = booksRepository;
    }

    @Transactional(readOnly = true)
    public CommentBook getById(long id) {
        Optional<CommentBook> comment;
        comment = commentsBookRepository.findById(id);
        return comment.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<CommentBook> getCommentsForBook(long bookId) {
        Optional<Book> book;
        book = booksRepository.findById(bookId);
        if (book.isEmpty()) {
            return Collections.emptyList();
        }
        return commentsBookRepository.findByBook(book.get());
    }

    @Transactional(readOnly = true)
    public List<CommentBook> getAll() {
        return commentsBookRepository.findAll();
    }

    @Override
    @Transactional
    public boolean addComment(String text, long bookId) {
        Optional<Book> book;
        CommentBook commentBook;
        book = booksRepository.findById(bookId);
        if (book.isPresent()){
            commentBook = new CommentBook(text, book.get());
            commentsBookRepository.save(commentBook);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
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
    @Transactional
    public boolean removeComment(long id) {
        Optional<CommentBook> commentBook;
        commentBook = commentsBookRepository.findById(id);
        commentBook.ifPresent(commentsBookRepository::delete);
        return true;
    }
}
