package ru.otus.library.dao;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import java.util.List;

public interface CommentBookDao {
    CommentBook getById(long id);

    List<CommentBook> getAll(Book book);

    List<CommentBook> getAll();

    CommentBook save(CommentBook comment);

    void deleteById(long id);
}
