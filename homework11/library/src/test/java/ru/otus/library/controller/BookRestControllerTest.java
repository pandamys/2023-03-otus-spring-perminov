package ru.otus.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDto;
import ru.otus.library.dto.BookNewAndChangeDto;
import ru.otus.library.dto.mapper.BookDtoMapper;
import ru.otus.library.repository.AuthorsRepository;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.GenreRepository;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "mongock.enabled=false")
public class BookRestControllerTest {
    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .build();

    @MockBean
    private BooksRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private AuthorsRepository authorRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("Получение списка книг")
    public void testAllBooks() throws Exception {
        Flux<Book> books = getTestBooks();
        Flux<BookDto> expectedResult = books
                .map(BookDtoMapper::toDto);
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> result = webClient.get()
                .uri("/api/book")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookDto.class)
                .hasSize(2)
                .returnResult().getResponseBody();
        assertNotNull(result);
        assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(expectedResult.blockFirst());
        assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(expectedResult.blockLast());
    }

    @Test
    @DisplayName("Отправка данных для создания книги")
    public void testCreateBook() throws Exception {
        String nameNewBook = "Test new book";
        String authorId = "222";
        String genreId = "333";
        Author author = new Author("Test", "Testov");
        Genre genre = new Genre("TestG");

        Book expectedBook = new Book(
                nameNewBook,
                author,
                genre);

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(Mono.just(expectedBook));
        Mockito.when(authorRepository.findById(Mockito.anyString())).thenReturn(Mono.just(author));
        Mockito.when(genreRepository.findById(Mockito.anyString())).thenReturn(Mono.just(genre));
        BookNewAndChangeDto newBook = new BookNewAndChangeDto(nameNewBook, authorId, genreId);

        BookNewAndChangeDto actualBook = webClient.post()
                    .uri("/api/book/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(newBook)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(BookNewAndChangeDto.class)
                    .returnResult().getResponseBody();

        assertNotNull(actualBook);
        assertEquals(expectedBook.getName(), actualBook.getName());
    }

    @Test
    @DisplayName("Отправка данных для обновления книги")
    public void testUpdateBook() throws Exception {
        Book book = getTestBooks().blockFirst();
        BookNewAndChangeDto expectedResult = new BookNewAndChangeDto(book.getId(), "New name book", book.getAuthor().getId(), book.getGenre().getId());
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(Mono.just(book));
        Mockito.when(authorRepository.findById(Mockito.anyString())).thenReturn(Mono.just(book.getAuthor()));
        Mockito.when(genreRepository.findById(Mockito.anyString())).thenReturn(Mono.just(book.getGenre()));

        BookNewAndChangeDto actualBook = webClient.put()
                .uri("/api/book/122sq")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(expectedResult)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookNewAndChangeDto.class)
                .returnResult().getResponseBody();

        assertNotNull(actualBook);
        assertNotEquals(expectedResult.getName(), actualBook.getName());
    }

    private Flux<Book> getTestBooks(){
        Author author;
        Genre genre;
        Book book1, book2;

        author = new Author("123", "Test", "Testov");
        genre = new Genre("1234", "Test_Genre");
        book1 = new Book("321", "TestBook1", author, genre);

        author = new Author("234", "Tester", "Testerov");
        genre = new Genre("2345", "Test_Genre");
        book2 = new Book("098", "TestBook2", author, genre);

        return Flux.just(book1, book2);
    }
}
