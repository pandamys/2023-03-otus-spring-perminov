package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;

@Repository
public class AuthorDaoJpa implements AuthorDao{
    @PersistenceContext
    private EntityManager em;

    public AuthorDaoJpa(EntityManager em){
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public Author getById(long id) {
        return em.find(Author.class, id);
    }
}
