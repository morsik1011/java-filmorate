package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    Long id;
    @NotEmpty(message = "Название не должно быть пустым  и null")
    String name;
    @Size(min = 1, max = 200)
    String description;
    LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    int duration;
}
