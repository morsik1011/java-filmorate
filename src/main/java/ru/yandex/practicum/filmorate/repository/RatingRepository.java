package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.RatingNotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.mapper.RatingMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RatingRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RatingMapper ratingMapper;

    public List<Rating> getAll() {
        String query = "SELECT * FROM rating";
        return jdbcTemplate.query(query, ratingMapper);
    }

    public Rating getById(Long id) {
        String query = "SELECT * FROM rating WHERE id = ? ";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, ratingMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new RatingNotFoundException("Рейтинг с таким id не найден " + id);
        }
    }
}


