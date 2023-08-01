package ru.otus.library.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorsRepository;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.GenreRepository;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {BookServiceImpl.class})
@EnableMethodSecurity
public class BookServiceImplTest {
    @MockBean
    private BooksRepository booksRepositoryMock;

    @MockBean
    private AuthorsRepository authorsRepositoryMock;

    @MockBean
    private GenreRepository genreRepositoryMock;

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("Test get book")
    public void testGetBook(){
        Optional<Book> expectedBook;
        Book actualBook;

        expectedBook = Optional.of(getTestBook());
        Mockito.when(booksRepositoryMock.findById(expectedBook.get().getId())).thenReturn(expectedBook);
        actualBook = bookService.getBookById(expectedBook.get().getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook.get());
    }

    @Test
    @DisplayName("Test remove book")
    @WithMockUser(value = "test_user", roles = "ADMIN")
    public void testRemoveBook(){
        Optional<Book> expectedBook;
        boolean actualResult;

        expectedBook = Optional.of(getTestBook());
        Mockito.when(booksRepositoryMock.findById(expectedBook.get().getId())).thenReturn(expectedBook);
        actualResult = bookService.removeBook(expectedBook.get().getId());
        assertEquals(actualResult, true);
    }

    @Test
    @DisplayName("Test not remove book without ADMIN role")
    @WithMockUser(value = "test_user", roles = "USER")
    public void testRemoveBookWithoutAdmin(){
        Optional<Book> expectedBook;
        Exception exception;

        expectedBook = Optional.of(getTestBook());
        Mockito.when(booksRepositoryMock.findById(expectedBook.get().getId())).thenReturn(expectedBook);
        exception = assertThrows(AccessDeniedException.class, () -> bookService.removeBook(expectedBook.get().getId()));
        assertEquals("Access Denied", exception.getMessage());
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
