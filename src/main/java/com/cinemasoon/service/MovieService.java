package com.cinemasoon.service;

import com.cinemasoon.entity.Movie;
import com.cinemasoon.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class MovieService {

    @PersistenceContext(unitName = "CinemaSoonPU")
    private EntityManager em;

    // Все фильмы (для главной)
    public List<Movie> findAll() {
        return em.createQuery("SELECT m FROM Movie m ORDER BY m.releaseDate", Movie.class)
                .getResultList();
    }

    // Фильмы по жанру
    public List<Movie> findByGenre(String genre) {
        if (genre == null || genre.isBlank()) {
            return findAll();
        }
        return em.createQuery(
                        "SELECT m FROM Movie m WHERE m.genre = :g ORDER BY m.releaseDate",
                        Movie.class)
                .setParameter("g", genre)
                .getResultList();
    }

    // Топ фильмов по лайкам
    public List<Movie> findTop(int limit) {
        return em.createQuery(
                        "SELECT m FROM Movie m WHERE m.likes > 0 ORDER BY m.likes DESC, m.releaseDate",
                        Movie.class)
                .setMaxResults(limit)
                .getResultList();
    }

    // Создать фильм
    public void create(Movie movie, Long userId) {
        User user = em.find(User.class, userId);
        movie.setUser(user);
        em.persist(movie);
    }

    // Обновить фильм (при редактировании)
    public void update(Movie movie) {
        em.merge(movie);
    }

    // Лайкнуть фильм
    public void like(Long id) {
        Movie m = em.find(Movie.class, id);
        if (m != null) {
            m.setLikes(m.getLikes() + 1);
        }
    }

    // Удалить фильм
    public void delete(Long id) {
        Movie m = em.find(Movie.class, id);
        if (m != null) {
            em.remove(m);
        }
    }
}
