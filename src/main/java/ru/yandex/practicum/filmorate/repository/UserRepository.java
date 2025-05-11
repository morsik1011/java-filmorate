package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.mapper.UserMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public List<User> getAll() {
        String query = "SELECT * FROM users";
        return jdbcTemplate.query(query, userMapper);
    }

    public User create(User user) {
        String query = "INSERT INTO users (name, login, email, birthday) VALUES (?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conection -> {
            PreparedStatement ps = conection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, user.getName());
            ps.setObject(2, user.getLogin());
            ps.setObject(3, user.getEmail());
            ps.setObject(4, user.getBirthday());

            return ps;
        }, keyHolder);
        Long id = keyHolder.getKeyAs(Long.class);
        if (id != null) {
            user.setId(id);
        } else {
            throw new RuntimeException("Не удалось сохранить данные");
        }
        return user;
    }

    public User getById(Long id) {
        try {
            String query = "SELECT * FROM users WHERE id = ? ";
            return jdbcTemplate.queryForObject(query, userMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Пользователь с id=" + id + " не найден");
        }
    }

    public User update(User user) {
        String query = "UPDATE users SET name = ?, login =?, email = ?, birthday = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(query, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
        if (rowsUpdated == 0) {
            String errorMessage = "Не удалось обновить данные";
            throw new UserNotFoundException(errorMessage);
        }
        return user;
    }

    public void addFriend(Long userId, Long friendId) {
        if (getById(userId) == null) {
            throw new UserNotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (getById(friendId) == null) {
            throw new UserNotFoundException("Пользователь с id=" + friendId + " не найден");
        }
        String query = "INSERT INTO friends (user_id, friend_id) VALUES(?,?)";

        int rowsUpdated = jdbcTemplate.update(query, userId, friendId);
        if (rowsUpdated == 0) {
            String errorMessage = "Не удалось обновить данные";
            throw new UserNotFoundException(errorMessage);
        }
    }


    public void removeFriend(Long userId, Long friendId) {
        if (getById(userId) == null) {
            throw new UserNotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (getById(friendId) == null) {
            throw new UserNotFoundException("Пользователь с id=" + friendId + " не найден");
        }
        String query = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(query, userId, friendId);
    }

    public List<User> getUserFriends(Long userId) {
        if (getById(userId) == null) {
            throw new UserNotFoundException("Пользователь с id=" + userId + " не найден");
        }
        String query = "SELECT u.* FROM users u JOIN friends f ON u.id = f.friend_id WHERE f.user_id = ?";
        return jdbcTemplate.query(query, userMapper, userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        if (getById(userId) == null) {
            throw new UserNotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (getById(otherId) == null) {
            throw new UserNotFoundException("Пользователь с id=" + otherId + " не найден");
        }
        String query = "SELECT u.* FROM users u " +
                "JOIN friends f1 ON u.id = f1.friend_id " +
                "JOIN friends f2 ON u.id = f2.friend_id " +
                "WHERE f1.user_id = ? AND f2.user_id = ?";
        return jdbcTemplate.query(query, userMapper, userId, otherId);
    }
}
