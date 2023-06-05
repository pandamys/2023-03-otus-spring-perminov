package ru.otus.library.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class CommentBook {
    @Id
    private String id;

    private String text;

    private Book book;

    public CommentBook(){

    }

    public CommentBook(String text, Book book) {
        this.text = text;
        this.book = book;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void printComment(){
        System.out.printf("[id: %s][Text: %s][Book: %s]\n", id, text, book.getName());
    }
}
