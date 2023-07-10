package ru.otus.library.dto.mapper;

import ru.otus.library.domain.CommentBook;
import ru.otus.library.dto.CommentBookDto;

public class CommentBookDtoMapper {
    public static CommentBookDto toDto(CommentBook commentBook) {
        return new CommentBookDto(commentBook.getId(), commentBook.getText(), commentBook.getBook().getId());
    }
}
