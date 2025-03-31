package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long id;
    String name;
    @NotEmpty(message = "Логин не должен быть null или пуст")
    String login;
    @Email(message = "Электронная почта не соответствует формату")
    @NotEmpty(message = "Электронная почта не может быть пустой")
    String email;
    @Past(message = "Дата рождения не может быть в будущем")
    LocalDate birthday;
}
