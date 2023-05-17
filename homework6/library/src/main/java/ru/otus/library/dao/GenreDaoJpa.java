package ru.otus.library.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;

@Transactional
@Repository
public class GenreDaoJpa implements GenreDao{
    private EntityManager em;

    public GenreDaoJpa(EntityManager em){
        this.em = em;
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }
}
