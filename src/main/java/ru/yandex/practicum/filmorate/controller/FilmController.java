package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен HTTP-запрос на создание фильма: {}", film);
        Film createdFilm = filmService.create(film);
        log.info("Успешно обработан HTTP-запрос на создание фильма: {}", film);
        return createdFilm;
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("Получен HTTP-запрос на получение всех фильмов");
        return filmService.getAll();
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен HTTP-запрос на обновление фильма: {}", film);
        Film updatedFilm = filmService.update(film);
        log.info("Успешно обработан HTTP-запрос на обновление фильма: {}", film);
        return updatedFilm;
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Long id) {
        log.info("Получен HTTP-запрос на получение фильма по id: {}", id);
        Film film = filmService.getById(id);
        log.debug("Успешно обработан HTTP-запрос, найденный фильм: {}", film);
        return film;
    }

    @PutMapping("/{id}/like/{user-id}")
    public void addLike(@PathVariable Long id, @PathVariable("user-id") Long userId) {
        log.info("Получен HTTP-запрос на лайк фильму с id: {}", id);
        filmService.addLike(id, userId);
        log.debug("Успешно поставлен лайк фильму с id: {}", id);
    }

    @DeleteMapping("/{id}/like/{user-id}")
    public void deleteLike(@PathVariable Long id, @PathVariable("user-id") Long userId) {
        log.info("Получен HTTP-запрос на удаление лайка фильма с id: {}", id);
        filmService.deleteLike(id, userId);
        log.debug("Успешно удален лайк у фильма с id: {}", id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(name = "count", required = false, defaultValue = "10") int count) {
        log.info("Получен HTTP-запрос на получение самых популярных фильмов.");
        List<Film> popularFilms = filmService.getPopularFilms(count);
        log.debug("Успешно обработан HTTP-запрос на получение самых популярных фильмов.");
        return popularFilms;
    }
}

