package ru.otus.library.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = BookServiceImplTest.class)
public class BookServiceImplTest {
    private BookService bookService;
    @MockBean
    private BookDao bookDaoMock;

    @BeforeEach
    public void setUp() {
        bookService = new BookServiceImpl(bookDaoMock);
    }

    @DisplayName("Test get book")
    @Test
    public void testGetBook(){
        Book expectedBook, actualBook;

        expectedBook = getTestBook();
        Mockito.when(bookDaoMock.getBookById(expectedBook.getId())).thenReturn(expectedBook);
        actualBook = bookService.getBookById(expectedBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Test remove book")
    @Test
    public void testRemoveBook(){
        Book expectedBook;
        boolean actualResult;

        expectedBook = getTestBook();
        Mockito.when(bookDaoMock.getBookById(expectedBook.getId())).thenReturn(expectedBook);
        actualResult = bookService.removeBook(expectedBook.getId());
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
