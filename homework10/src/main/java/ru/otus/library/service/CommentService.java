package ru.otus.library.service;

import ru.otus.library.domain.CommentBook;

import java.util.List;

public interface CommentService {
    CommentBook getById(long id);

    List<CommentBook> getCommentsForBook(long bookId);

    List<CommentBook> getAll();

    boolean addComment(String text, long bookId);

    void updateComment(long id, String text, long bookId);

    boolean removeComment(long id);
}
