package ru.otus.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDto;
import ru.otus.library.dto.BookDtoMapper;
import ru.otus.library.dto.BookNewAndChangeDto;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvcMock;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    BookDtoMapper bookDtoMapperMock;

    @Test
    @DisplayName("Получение списка книг")
    public void testAllBooks() throws Exception {
        List<Book> books = getTestBooks();
        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        String content = mvcMock.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(content.contains("TestBook1"));
        assertTrue(content.contains("TestBook2"));
    }

    @Test
    @DisplayName("Получение конкретной книги")
    public void testGetBook() throws Exception {
        Book book = getTestBooks().get(0);
        Mockito.when(bookService.getBookById(Mockito.anyLong())).thenReturn(book);
        String content = mvcMock.perform(get("/view/book")
                        .param("id", String.valueOf(book.getId())))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

        assertTrue(content.contains(book.getName()));
        assertTrue(content.contains(book.getAuthor().getFullName()));
        assertTrue(content.contains(book.getGenre().getName()));

    }

    @Test
    @DisplayName("Отправка данных для создания книги")
    public void testCreateBook() throws Exception {
        String nameNewBook = "Test new book";
        Long authorId = 2L;
        Long genreId = 1L;

        Mockito.when(bookService.addBook(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);

        mvcMock.perform(post("/add/book")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", nameNewBook)
                .param("authorId", String.valueOf(authorId))
                .param("genreId", String.valueOf(genreId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books"));
    }

    @Test
    @DisplayName("Отправка данных для удаления книги")
    public void testDeleteBook() throws Exception {
        Long id = 2L;

        Mockito.when(bookService.removeBook(id)).thenReturn(true);

        mvcMock.perform(get("/delete/book")
                .param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books"));
    }

    private List<Book> getTestBooks(){
        List<Book> books = new ArrayList<>();
        Author author;
        Genre genre;
        Book book;

        author = new Author(1, "Test", "Testov");
        genre = new Genre(1, "Test_Genre");
        book = new Book(1, "TestBook1", author, genre);
        books.add(book);

        author = new Author(1, "Tester", "Testerov");
        genre = new Genre(1, "Test_Genre");
        book = new Book(1, "TestBook2", author, genre);
        books.add(book);

        return books;
    }
}
