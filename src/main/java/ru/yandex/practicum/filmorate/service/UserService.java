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
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        if (user.getFriendsId().contains(friendId)) {
            throw new UserAddFriendsException(String.format("Пользователь с id %d уже в списке друзей", friendId));
        }
        user.setFriendId(friendId);
        friend.setFriendId(userId);
        userStorage.update(user);
        userStorage.update(friend);
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);
        user.removeFriendId(friendId);
        friend.removeFriendId(userId);
        userStorage.update(user);
        userStorage.update(friend);
    }

    public List<User> listOfUserFriends(Long userId) {
        User user = userStorage.getById(userId);

        return user.getFriendsId().stream()
                .map(userStorage::getById)
                .toList();
    }

    public List<User> listOfCommonFriends(Long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        return user.getFriendsId().stream()
                .filter(friend.getFriendsId()::contains)
                .map(userStorage::getById)
                .toList();
    }
}

