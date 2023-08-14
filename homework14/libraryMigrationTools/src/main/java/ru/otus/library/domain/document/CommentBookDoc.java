package ru.otus.library.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class CommentBookDoc {
    @Id
    private String id;

    private String text;

    private BookDoc book;

    public CommentBookDoc() {

    }

    public CommentBookDoc(String text, BookDoc book) {
        this.text = text;
        this.book = book;
    }

    public CommentBookDoc(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BookDoc getBook() {
        return book;
    }

    public void setBook(BookDoc book) {
        this.book = book;
    }

    public void printComment() {
        System.out.printf("[id: %s][Text: %s][Book: %s]\n", id, text, book.getName());
    }
}
