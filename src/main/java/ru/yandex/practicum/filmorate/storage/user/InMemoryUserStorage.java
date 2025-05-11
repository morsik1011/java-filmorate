package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final UserRepository userRepository;

    public InMemoryUserStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
   return userRepository.create(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User update(User user) {
    return userRepository.update(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void addFriend(Long user_id, Long friend_id){userRepository.addFriend(user_id, friend_id);}

    @Override
    public void removeFriend(Long userId,Long  friendId){userRepository.removeFriend(userId,friendId);};

    @Override
    public List<User> getUserFriends (Long userId){ return userRepository.getUserFriends(userId);}

    @Override
    public  List<User> getCommonFriends (Long userId, Long friendId){ return userRepository.getCommonFriends(userId,friendId);
    }
}


