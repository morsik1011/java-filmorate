package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserAddFriendsException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User getById(Long id) {
        return userStorage.getById(id);
    }

    public void addFriend(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new UserAddFriendsException("Пользователь не может добавить себя в друзья");
        }
        userStorage.addFriend(userId, friendId);
        }

    public void removeFriend(Long userId, Long friendId) {
        userStorage.removeFriend(userId, friendId);

    }

    public List<User> listOfUserFriends(Long userId) {
        return userStorage.getUserFriends(userId);

    }

    public List<User> listOfCommonFriends(Long userId, Long friendId) {
        return userStorage.getCommonFriends(userId, friendId);
    }
}

