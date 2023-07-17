package ru.otus.library.dto;

public class CommentBookDto {
    private String id;

    private String text;

    private String bookId;

    public CommentBookDto(String id, String text, String bookId) {
        this.id = id;
        this.text = text;
        this.bookId = bookId;
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
}
