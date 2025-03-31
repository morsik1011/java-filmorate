package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен HTTP-запрос на создание пользователя: {}", user);
        User createdUser = userService.create(user);
        log.info("Успешно обработан HTTP-запрос на создание пользователя: {}", user);
        return createdUser;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Получен HTTP-запрос на получение всех пользователей");
        return userService.getAll();
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен HTTP-запрос на обновление пользователя: {}", user);
        User updatedUser = userService.update(user);
        log.info("Успешно обработан HTTP-запрос на обновление пользователя: {}", user);
        return updatedUser;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        log.info("Получен HTTP-запрос на получение пользователя по id: {}", id);
        User user = userService.getById(id);
        log.debug("Найденный пользователь: {}", user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен HTTP-запрос на добавление в друзья пользователя с id: {}", friendId);
        userService.addFriend(id, friendId);
        log.info("Успешно обработан HTTP-запрос на добавление в друзья пользователя с id: {}", friendId);

    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен HTTP-запрос на удаление из друзей пользователя с id: {}", friendId);
        userService.removeFriend(id, friendId);
        log.info("Успешно обработан HTTP-запрос на удаление из друзей пользователя с id: {}", friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> listOfUserFriends(@PathVariable Long id) {
        log.info("Получен HTTP-запрос на получение списка друзей пользователя с id: {}", id);
        List<User> userFriends = userService.listOfUserFriends(id);
        log.info("Успешно обработан HTTP-запрос на получение списка друзей пользователя с id: {}", id);
        return userFriends;
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> listOfCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Получен HTTP-запрос на получение списка общих друзей пользователей с id: {}, {}", id, otherId);
        List<User> usersFriends = userService.listOfCommonFriends(id, otherId);
        log.info("Успешно обработан HTTP-запрос на получение списка общих друзей пользователей с id: {}, {}", id, otherId);
        return usersFriends;
    }
}

