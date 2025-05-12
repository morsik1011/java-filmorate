package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    //    GET /genres — возвращает список объектов содержащих жанр
    @GetMapping
    public List<Genre> getAll() {
        log.info("Получен HTTP-запрос на получение списка объектов содержащих жанр");
        return genreService.getAll();
    }

    //    GET /genres/{id} — возвращает объект содержащий жанр с идентификатором id
    @GetMapping("/{id}")
    public Genre getById(@PathVariable Long id) {
        log.info("Получен HTTP-запрос на получение объекта содержащего жанр с идентификатором id: {}", id);
        Genre genre = genreService.getById(id);
        log.debug("Успешно обработан HTTP-запрос, найденный жанр: {}", genre);
        return genre;
    }
}
