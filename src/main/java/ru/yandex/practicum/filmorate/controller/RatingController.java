package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class RatingController {

    private final RatingService ratingService;


    @GetMapping
    public List<Rating> getAll() {
        log.info("Получен HTTP-запрос на получение списка объектов содержащих рейтинг");
        return ratingService.getAll();
    }

    @GetMapping("/{id}")
    public Rating getById(@PathVariable Long id) {
        log.info("Получен HTTP-запрос на получение объекта содержащего рейтинг с идентификатором id: {}", id);
        return ratingService.getById(id);
    }
}
