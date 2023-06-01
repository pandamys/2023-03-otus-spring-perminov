package ru.otus.library.service;

import ru.otus.library.domain.CommentBook;

import java.util.List;

public interface CommentService {
    CommentBook getById(String id);

    List<CommentBook> getCommentsForBook(String bookId);

    List<CommentBook> getAll();

    boolean addComment(String text, String bookId);

    void updateComment(String id, String text, String bookId);

    boolean removeComment(String id);
}
