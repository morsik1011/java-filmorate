package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ReleaseDataException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private Map<Long, Film> idToFilm = new HashMap<>();
    private Long idCounter = 1L;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен HTTP-запрос на создание фильма: {}", film);
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            String errorMessage = "Дата релиза не должна быть раньше 28 декабря 1895 года";
            log.error(errorMessage);
            throw new ReleaseDataException(errorMessage);
        }
        film.setId(idCounter++);
        idToFilm.put(film.getId(), film);
        log.info("Успешно обработан HTTP-запрос на создание фильма: {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("Получен HTTP-запрос на получение всех фильмов");
        return new ArrayList<>(idToFilm.values());
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен HTTP-запрос на обновление фильма: {}", film);
        Long id = film.getId();

        if (!idToFilm.containsKey(id)) {
            String errorMessage = String.format("Фильм с id %d не найден", id);
            log.error(errorMessage);
            throw new FilmNotFoundException(errorMessage);
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            String errorMessage = "Дата релиза не должна быть раньше 28 декабря 1895 года";
            log.error(errorMessage);
            throw new ReleaseDataException(errorMessage);
        }
        idToFilm.put(id, film);
        log.info("Успешно обработан HTTP-запрос на обновление фильма: {}", film);
        return film;
    }
}

