package ru.yandex.practicum.filmorate.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class GenreMapper implements RowMapper<Genre> {


@Override
public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    Long id = resultSet.getLong("id");
    String name = resultSet.getString("name");

    return Genre.builder()
            .id(id)
            .name(name)
            .build();
}
}
