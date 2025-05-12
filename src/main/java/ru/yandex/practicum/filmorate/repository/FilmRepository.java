package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mapper.FilmMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;


@Component
@RequiredArgsConstructor
public class FilmRepository {
    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;

    public List<Film> getAll() {
        String query = "SELECT * FROM films";
        List<Film> films = jdbcTemplate.query(query, filmMapper);
        return films.isEmpty() ? Collections.emptyList() : films;
    }

    public Film create(Film film) {

        if (film.getMpa() == null || film.getMpa().getId() == null) {
            throw new RatingNotFoundException("MPA рейтинг не указан");
        }
        ratingRepository.getById(film.getMpa().getId());

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            String errorMessage = "Дата релиза не должна быть раньше 28 декабря 1895 года";
            throw new ReleaseDataException(errorMessage);
        }

        String query = "INSERT INTO films (name, description, release_date, duration, rating_id) VALUES (?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conection -> {
            PreparedStatement ps = conection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, film.getName());
            ps.setObject(2, film.getDescription());
            ps.setObject(3, film.getReleaseDate());
            ps.setObject(4, film.getDuration());
            ps.setLong(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        film.setId(id);

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            Set<Genre> uniqueGenres = new HashSet<>(film.getGenres());
            uniqueGenres.forEach(genre ->
                    genreRepository.addGenreToFilm(film.getId(), genre.getId()));
        }

        return film;
    }

    public Film getById(Long id) {
        try {
            String query = "SELECT * FROM films WHERE id = ? ";
            return jdbcTemplate.queryForObject(query, filmMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException("Фильм с id=" + id + " не найден");
        }
    }

    public Film update(Film film) {
        String query = "UPDATE films SET name = ?, description =?, release_date = ?, duration = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(query, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            String errorMessage = "Дата релиза не должна быть раньше 28 декабря 1895 года";
            throw new ReleaseDataException(errorMessage);
        }
        genreRepository.removeAllGenresFromFilm(film.getId());
        if (film.getGenres() != null) {
            film.getGenres().forEach(genre ->
                    genreRepository.addGenreToFilm(film.getId(), genre.getId()));
        }
        if (rowsUpdated == 0) {
            String errorMessage = "Не удалось обновить данные";
            throw new FilmNotFoundException(errorMessage);
        }
        return film;
    }

    public void addLike(Long filmId, Long userId) {
        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        String sql = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        String sql = "SELECT f.* FROM films f " +
                "LEFT JOIN likes l ON f.id = l.film_id " +
                "GROUP BY f.id " +
                "ORDER BY COUNT(l.user_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sql, filmMapper, count);
    }
}
