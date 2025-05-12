package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final FilmRepository filmRepository;
    private final Map<Long, Film> idToFilm = new HashMap<>();
    private Long idCounter = 1L;

    public InMemoryFilmStorage(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film create(Film film) {
        return filmRepository.create(film);
    }

    @Override
    public Film update(Film film) {
        return filmRepository.update(film);
    }

    @Override
    public List<Film> getAll() {
        return filmRepository.getAll();
    }

    @Override
    public Film getById(Long id) {
        return filmRepository.getById(id);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        filmRepository.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        filmRepository.deleteLike(filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }
}
