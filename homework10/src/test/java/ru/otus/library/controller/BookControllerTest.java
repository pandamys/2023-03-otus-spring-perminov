package ru.otus.library.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.otus.library.dto.BookNewAndChangeDto;
import ru.otus.library.dto.mapper.BookDtoMapper;
import ru.otus.library.rest.BookRestController;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookRestController.class)
public class BookControllerTest {
    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .build();

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
        List<BookDto> expectedResult = books.stream()
                .map(BookDtoMapper::toDto).toList();
        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        mvcMock.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("Получение конкретной книги")
    public void testGetBook() throws Exception {
        Book book = getTestBooks().get(0);
        BookDto expectedResult = BookDtoMapper.toDto(book);
        Mockito.when(bookService.getBookById(Mockito.anyLong())).thenReturn(book);
        mvcMock.perform(get("/api/book/" + book.getId()))
                        .andExpect(status().isOk())
                        .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
    }

    @Test
    @DisplayName("Отправка данных для создания книги")
    public void testCreateBook() throws Exception {
        String nameNewBook = "Test new book";
        Long authorId = 2L;
        Long genreId = 1L;

        Mockito.when(bookService.addBook(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(getTestBooks().get(0));
        BookNewAndChangeDto newBook = new BookNewAndChangeDto(nameNewBook, authorId, genreId);

        mvcMock.perform(post("/api/book/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Отправка данных для обновления книги")
    public void testUpdateBook() throws Exception {
        Book book = getTestBooks().get(0);
        BookNewAndChangeDto expectedResult = new BookNewAndChangeDto(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId());
        Mockito.when(bookService.updateBook(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(book);

        mvcMock.perform(put("/api/book/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedResult)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отправка данных для удаления книги")
    public void testDeleteBook() throws Exception {
        Long id = 2L;

        Mockito.when(bookService.removeBook(id)).thenReturn(true);

        mvcMock.perform(delete("/api/book/" + id))
                .andExpect(status().isOk());
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
