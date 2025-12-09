package com.cinemasoon.web;

import com.cinemasoon.entity.Movie;
import com.cinemasoon.service.MovieService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MovieBean implements Serializable {

    @Inject
    private MovieService movieService;

    private List<Movie> movies;
    private Movie newMovie = new Movie();
    private Movie editingMovie;

    // выбранный жанр для страницы genres.xhtml
    private String selectedGenre;

    // пока фиксированный пользователь
    private Long currentUserId = 1L;

    @PostConstruct
    public void init() {
        movies = movieService.findAll();
    }

    // ===== СОЗДАНИЕ =====
    public void addMovie() {
        movieService.create(newMovie, currentUserId);
        newMovie = new Movie();
        movies = movieService.findAll();
    }

    // ===== УДАЛЕНИЕ =====
    public void deleteMovie(Long id) {
        movieService.delete(id);
        movies = movieService.findAll();
    }

    // ===== РЕДАКТИРОВАНИЕ =====
    public void startEdit(Movie movie) {
        editingMovie = movie;
    }

    public void saveEdit() {
        movieService.update(editingMovie);
        editingMovie = null;
        movies = movieService.findAll();
    }

    public void cancelEdit() {
        editingMovie = null;
    }

    public boolean isEditing() {
        return editingMovie != null;
    }

    // ===== ЛАЙК =====
    public void likeMovie(Long id) {
        movieService.like(id);
        movies = movieService.findAll();
    }

    // ===== ЖАНРЫ =====
    public String getSelectedGenre() {
        return selectedGenre;
    }

    public void setSelectedGenre(String selectedGenre) {
        this.selectedGenre = selectedGenre;
    }

    public List<Movie> getMoviesByGenre() {
        if (selectedGenre == null || selectedGenre.isBlank()) {
            return movies;
        }
        return movieService.findByGenre(selectedGenre);
    }

    // ===== ТОП =====
    public List<Movie> getTopMovies() {
        return movieService.findTop(5);
    }

    // ===== ГЕТТЕРЫ / СЕТТЕРЫ =====

    public List<Movie> getMovies() {
        return movies;
    }

    public Movie getNewMovie() {
        return newMovie;
    }

    public void setNewMovie(Movie newMovie) {
        this.newMovie = newMovie;
    }

    public Movie getEditingMovie() {
        return editingMovie;
    }

    public void setEditingMovie(Movie editingMovie) {
        this.editingMovie = editingMovie;
    }
}
