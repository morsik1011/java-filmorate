package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mapper.GenreMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreRepository {
    private final JdbcTemplate jdbcTemplate;
    private final GenreMapper genreMapper;

    public List<Genre> getAll() {
        String query = "SELECT * FROM genres";
        return jdbcTemplate.query(query, genreMapper);
    }

    public Genre getById(Long id) {
        String query = "SELECT * FROM genres WHERE id = ? ";
        try {
            return jdbcTemplate.queryForObject(query, genreMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException("Жанр с таким id не найден " + id);
        }
    }

    public List<Genre> getFilmGenres(Long filmId) {
        String query = "SELECT g.* FROM genres g JOIN film_genre fg ON g.id = fg.genre_id WHERE fg.film_id = ? ORDER BY g.id";
        return jdbcTemplate.query(query, genreMapper, filmId);
    }

    public void addGenreToFilm(Long filmId, Long genreId) {
        try {
            Genre genre = getById(genreId);
        } catch (GenreNotFoundException e) {
            throw new GenreNotFoundException("Жанр с id=" + genreId + " не найден.");
        }
        String query = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.update(query, filmId, genreId);
    }

    public void removeAllGenresFromFilm(Long filmId) {
        String query = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(query, filmId);
    }
}


