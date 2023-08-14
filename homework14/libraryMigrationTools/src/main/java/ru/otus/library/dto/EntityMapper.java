package ru.otus.library.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.document.AuthorDoc;
import ru.otus.library.domain.document.BookDoc;
import ru.otus.library.domain.document.CommentBookDoc;
import ru.otus.library.domain.document.GenreDoc;
import ru.otus.library.domain.entity.Author;
import ru.otus.library.domain.entity.Book;
import ru.otus.library.domain.entity.CommentBook;
import ru.otus.library.domain.entity.Genre;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class EntityMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    public AuthorDoc toAuthorDocument(Author author){
        return new AuthorDoc(author.getName(), author.getSurname(), author.getId());
    }

    public GenreDoc toGenreDocument(Genre genre){
        return new GenreDoc(genre.getName(), genre.getId());
    }

    public BookDoc toBookDocument(Book book){
        return new BookDoc(book.getName(),
                findAuthorByPreviousId(book.getAuthor()),
                findGenreByPreviousId(book.getGenre()),
                book.getId());
    }

    public CommentBookDoc toCommentBook(CommentBook commentBook){
        return new CommentBookDoc(commentBook.getText(), findBookByPreviousId(commentBook.getBook()));
    }

    protected GenreDoc findGenreByPreviousId(Genre genre) {
        return mongoTemplate.findOne(query(where("previousId").is(genre.getId())), GenreDoc.class);
    }

    protected AuthorDoc findAuthorByPreviousId(Author genre) {
        return mongoTemplate.findOne(query(where("previousId").is(genre.getId())), AuthorDoc.class);
    }

    protected BookDoc findBookByPreviousId(Book book) {
        return mongoTemplate.findOne(query(where("previousId").is(book.getId())), BookDoc.class);
    }
}
