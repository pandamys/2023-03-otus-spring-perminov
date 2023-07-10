package ru.otus.library.dto;

public class CommentBookDto {
    private Long id;
    private String text;
    private Long bookId;

    public CommentBookDto(Long id, String text, Long bookId) {
        this.id = id;
        this.text = text;
        this.bookId = bookId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
