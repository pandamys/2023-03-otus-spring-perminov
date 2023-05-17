package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import java.util.List;

@Repository
public class BookDaoJpa implements BookDao{
    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query;
        query = em.createQuery("select b " +
                "from Book b " +
                "where b.name = :name", Book.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Book insert(Book book) {
        if (book.getId() <= 0){
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void update(Book book) {
        Query query;
        query = em.createQuery("update Book b " +
                "set b.name = :name," +
                "b.author = :author, " +
                "b.genre = :genre " +
                "where b.id = :id");
        query.setParameter("name", book.getName());
        query.setParameter("author", book.getAuthor());
        query.setParameter("genre", book.getGenre());
    }

    @Override
    public void deleteById(long id) {
        Query query;
        query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
