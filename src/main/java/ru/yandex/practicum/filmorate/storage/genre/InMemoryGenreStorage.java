package ru.yandex.practicum.filmorate.storage.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreRepository;

import java.util.List;

@Slf4j
@Component
public class InMemoryGenreStorage implements GenreStorage {
    private final GenreRepository genreRepository;

    public InMemoryGenreStorage(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }

    @Override
    public Genre getById(Long id) {
        return genreRepository.getById(id);
    }
}
