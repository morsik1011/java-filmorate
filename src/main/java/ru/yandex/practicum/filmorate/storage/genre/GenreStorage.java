package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public class GenreStorage {
    
    List<String> getAll();

    String getById(Long id);
}
