package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User create(User user);

    List<User> getAll();

    User update(User user);

    User getById(Long id);
}

