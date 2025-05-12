package ru.yandex.practicum.filmorate.storage.rating;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.RatingRepository;

import java.util.List;

@Slf4j
@Component
public class InMemoryRatingStorage implements RatingStorage {
    private final RatingRepository ratingRepository;

    public InMemoryRatingStorage(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public List<Rating> getAll() {
        return ratingRepository.getAll();
    }

    @Override
    public Rating getById(Long id) {
        return ratingRepository.getById(id);
    }
}
