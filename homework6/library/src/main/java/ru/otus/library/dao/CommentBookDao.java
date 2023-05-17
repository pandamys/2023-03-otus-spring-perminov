package ru.otus.library.dao;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import java.util.List;

public interface CommentBookDao {
    CommentBook getById(long id);

    List<CommentBook> getComments(Book book);

    List<CommentBook> getAll();

    CommentBook insertCommentBook(CommentBook comment);

    void updateCommentBook(CommentBook comment);

    void deleteCommentBook(long id);
}
