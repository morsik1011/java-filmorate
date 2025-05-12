package ru.yandex.practicum.filmorate.repository.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String login = resultSet.getString("login");
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();

        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .login(login)
                .birthday(birthday)
                .build();
    }
}