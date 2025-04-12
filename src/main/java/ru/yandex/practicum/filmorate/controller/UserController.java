package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private Map<Long, User> idToUser = new HashMap<>();
    private Long idCounter = 1L;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен HTTP-запрос на создание пользователя: {}", user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(idCounter++);
        idToUser.put(user.getId(), user);
        log.info("Успешно обработан HTTP-запрос на создание пользователя: {}", user);
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Получен HTTP-запрос на получение всех пользователей");
        return new ArrayList<>(idToUser.values());
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен HTTP-запрос на обновление пользователя: {}", user);
        Long id = user.getId();

        if (!idToUser.containsKey(id)) {
            String errorMessage = String.format("Пользователь с id %d не найден", id);
            log.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }

        idToUser.put(id, user);
        log.info("Успешно обработан HTTP-запрос на обновление пользователя: {}", user);
        return user;
    }
}
