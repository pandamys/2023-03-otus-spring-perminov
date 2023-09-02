package ru.otus.library.dto;

import ru.otus.library.domain.Book;

public class BookDtoMapper {
    public static Book toDomainObject(BookDto bookDto) {
        return new Book(bookDto);
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book);
    }
}
