package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    Long id;
    @NotBlank(message = "Название не должно быть пустым  и null")
    String name;
    @Size(min = 1, max = 200)
    String description;
    LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    int duration;

    Set<Long> likes = new HashSet<>();
    List<Genre> genres;
    Rating mpa;

    public void setLike(Long userId) {
        likes.add(userId);
    }

    public void removeLike(Long userId) {
        likes.remove(userId);
    }

}
