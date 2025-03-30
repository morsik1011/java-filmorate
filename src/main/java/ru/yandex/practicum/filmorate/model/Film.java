package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Long id;
    @NotEmpty(message = "Название не должно быть null или пустым")
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private int duration;
}
