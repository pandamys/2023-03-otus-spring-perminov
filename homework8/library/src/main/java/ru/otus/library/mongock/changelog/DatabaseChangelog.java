package ru.otus.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.CommentBook;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorsRepository;
import ru.otus.library.repository.BooksRepository;
import ru.otus.library.repository.CommentsBookRepository;
import ru.otus.library.repository.GenreRepository;

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

    @ChangeSet(order = "001", id = "dropDb", author = "perminovva", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "perminovva")
    public void insertAuthors(AuthorsRepository authorsRepository) {
        tarmashev = authorsRepository.save(new Author("Sergey", "Tarmashev"));
        gluhovskii = authorsRepository.save(new Author("Dmitrii", "Gluhovskii"));
        deChensi = authorsRepository.save(new Author("John De", "Chensi"));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "perminovva")
    public void insertGenres(GenreRepository genreRepository) {
        fantastic = genreRepository.save(new Genre("Fantastic"));
        fantasy = genreRepository.save(new Genre("Fantasy"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "perminovva")
    public void insertBook(BooksRepository booksRepository) {
        drevnii = booksRepository.save(new Book("Drevnii", tarmashev, fantastic));
        metro2033 = booksRepository.save(new Book("Metro 2033", gluhovskii, fantastic));
        zamok = booksRepository.save(new Book("Zamok brodiachii", deChensi, fantasy));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "perminovva")
    public void insertComments(CommentsBookRepository commentsBookRepository) {
        commentsBookRepository.save(new CommentBook("Cool book", drevnii));
        commentsBookRepository.save(new CommentBook("Very interesting book", metro2033));
    }
}
