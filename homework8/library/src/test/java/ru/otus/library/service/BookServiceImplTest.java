package ru.otus.library.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorsRepository;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = BookServiceImplTest.class)
public class BookServiceImplTest {
    private BookService bookService;
    @MockBean
    private BooksRepository booksRepositoryMock;
    @MockBean
    private AuthorsRepository authorsRepositoryMock;
    @MockBean
    private GenreRepository genreRepositoryMock;

    @BeforeEach
    public void setUp() {
        bookService = new BookServiceImpl(booksRepositoryMock, authorsRepositoryMock, genreRepositoryMock);
    }

    @DisplayName("Test get book")
    @Test
    public void testGetBook(){
        Optional<Book> expectedBook;
        Book actualBook;

        expectedBook = Optional.of(getTestBook());
        Mockito.when(booksRepositoryMock.findByName(expectedBook.get().getId())).thenReturn(expectedBook);
        actualBook = bookService.getBookById(expectedBook.get().getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook.get());
    }

    @DisplayName("Test remove book")
    @Test
    public void testRemoveBook(){
        Optional<Book> expectedBook;
        boolean actualResult;

        expectedBook = Optional.of(getTestBook());
        Mockito.when(booksRepositoryMock.findById(expectedBook.get().getId())).thenReturn(expectedBook);
        actualResult = bookService.removeBook(expectedBook.get().getId());
        Assertions.assertThat(actualResult).isEqualTo(true);
    }

    private Book getTestBook(){
        Author author;
        Genre genre;
        Book book;

        author = new Author(1, "Test", "Testov");
        genre = new Genre(1, "Test_Genre");
        book = new Book(1, "Test_Book", author, genre);
        return book;
    }
}
