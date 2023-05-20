package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.CommentBookDao;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;

import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentBookDao commentBookDao;

    private final BookDao bookDao;

    public CommentServiceImpl(CommentBookDao commentBookDao,
                              BookDao bookDao) {
        this.commentBookDao = commentBookDao;
        this.bookDao = bookDao;
    }

    @Override
    @Transactional(readOnly = true)
    public CommentBook getById(long id) {
        return commentBookDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentBook> getCommentsForBook(long bookId) {
        Book book;
        book = bookDao.getById(bookId);
        if (book == null) {
            return Collections.emptyList();
        }
        return commentBookDao.getAll(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentBook> getAll() {
        return commentBookDao.getAll();
    }

    @Override
    @Transactional
    public boolean addComment(String text, long bookId) {
        Book book;
        CommentBook commentBook;
        book = bookDao.getById(bookId);
        if (book != null){
            commentBook = new CommentBook(text, book);
            commentBookDao.save(commentBook);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void updateComment(long id, String text, long bookId) {
        CommentBook commentBook;
        Book book;
        commentBook = commentBookDao.getById(id);
        if (text != null && !text.equals("")) {
                commentBook.setText(text);
        }
        if (bookId > 0) {
            book = bookDao.getById(bookId);
            if (book != null) {
                commentBook.setBook(book);
            }
        }
        commentBookDao.save(commentBook);
    }

    @Override
    @Transactional
    public boolean removeComment(long id) {
        CommentBook commentBook;
        commentBook = commentBookDao.getById(id);
        if (commentBook == null){
            return false;
        }
        commentBookDao.deleteById(id);
        return true;
    }
}
