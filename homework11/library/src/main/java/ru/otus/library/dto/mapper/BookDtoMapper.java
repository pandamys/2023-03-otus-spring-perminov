package ru.otus.library.dto.mapper;

import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;

public class BookDtoMapper {
    public static Book toDomainObject(BookDto bookDto) {
        return new Book(bookDto);
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book);
    }
}
