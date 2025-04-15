package ru.yandex.practicum.filmorate.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exceptions.FilmAddLikeException;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;

@Service
public class FilmService {

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(UserStorage userStorage, FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film getById(Long id) {
        return filmStorage.getById(id);
    }

    public void addLike(Long filmId, Long userId) {
        User user = userStorage.getById(userId);
        Film film = filmStorage.getById(filmId);
        if (film.getLikes().contains(userId)) {
            throw new FilmAddLikeException(String.format("Пользователь с id: %d уже поставил like", userId));
        }
        film.setLike(user.getId());
    }

    public void deleteLike(Long filmId, Long userId) {
        User user = userStorage.getById(userId);
        Film film = filmStorage.getById(filmId);
        film.removeLike(user.getId());
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getAll()
                .stream()
                .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
                .limit(count)
                .toList();
    }
}
