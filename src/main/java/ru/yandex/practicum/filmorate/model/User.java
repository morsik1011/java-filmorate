package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    @NotEmpty(message = "Логин не должен быть null или пуст")
    private String login;
    @Email(message = "Электронная почта не соответствует формату")
    @NotEmpty(message = "Электронная почта не может быть пустой")
    private String email;
    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
