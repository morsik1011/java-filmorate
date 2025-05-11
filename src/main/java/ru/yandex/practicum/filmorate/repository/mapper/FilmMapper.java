package ru.yandex.practicum.filmorate.repository.mapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.GenreRepository;
import ru.yandex.practicum.filmorate.repository.RatingRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FilmMapper implements RowMapper<Film> {
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;
    private final JdbcTemplate jdbcTemplate;

    public FilmMapper(GenreRepository genreRepository, RatingRepository ratingRepository, JdbcTemplate jdbcTemplate) {
        this.genreRepository = genreRepository;
        this.ratingRepository = ratingRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
        int duration = resultSet.getInt("duration");
        Long mpaId = resultSet.getLong("rating_id");

        Film film = Film.builder()
                .id(id)
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .build();

        film.setMpa(ratingRepository.getById(mpaId));
        film.setGenres(genreRepository.getFilmGenres(film.getId()));
        film.setLikes(getLikes(film.getId()));
        return film;
    }

    private Set<Long> getLikes(Long filmId) {
        String query = "SELECT user_id FROM likes WHERE film_id = ?";
        return new HashSet<>(jdbcTemplate.queryForList(query, Long.class, filmId));
    }
    }
