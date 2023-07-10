package ru.otus.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorsRepository;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.repository.CommentsBookRepository;

@ChangeLog
public class DatabaseChangelog {
    private Book drevnii;

    private Book metro2033;

    private Book zamok;

    private Author tarmashev;

    private Author gluhovskii;

    private Author deChensi;

    private Genre fantastic;

    private Genre fantasy;

    private CommentBook tempCommentBook;

    @ChangeSet(order = "002", id = "insertAuthors", author = "perminovva")
    public void insertAuthors(AuthorsRepository authorsRepository) {
        tarmashev = authorsRepository.save(new Author("Sergey", "Tarmashev")).block();
        gluhovskii = authorsRepository.save(new Author("Dmitrii", "Gluhovskii")).block();
        deChensi = authorsRepository.save(new Author("John De", "Chensi")).block();
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "perminovva")
    public void insertGenres(GenreRepository genreRepository) {
        fantastic = genreRepository.save(new Genre("Fantastic")).block();
        fantasy = genreRepository.save(new Genre("Fantasy")).block();
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "perminovva")
    public void insertBook(BooksRepository booksRepository) {
        drevnii = booksRepository.save(new Book("Drevnii", tarmashev, fantastic)).block();
        metro2033 = booksRepository.save(new Book("Metro 2033", gluhovskii, fantastic)).block();
        zamok = booksRepository.save(new Book("Zamok brodiachii", deChensi, fantasy)).block();
    }

    @ChangeSet(order = "005", id = "insertComments", author = "perminovva")
    public void insertComments(CommentsBookRepository commentsBookRepository,
                               BooksRepository booksRepository) {
        tempCommentBook = commentsBookRepository.save(new CommentBook("Cool book", drevnii)).block();
        drevnii.setComments(tempCommentBook);
        drevnii = booksRepository.save(drevnii).block();
        tempCommentBook = commentsBookRepository.save(new CommentBook("Very interesting book", metro2033)).block();
        metro2033.setComments(tempCommentBook);
        metro2033 = booksRepository.save(metro2033).block();
    }
}