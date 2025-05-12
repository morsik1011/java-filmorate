package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long id;
    String name;
    @NotBlank(message = "Логин не должен быть null или пуст")
    String login;
    @Email(message = "Электронная почта не соответствует формату")
    @NotBlank(message = "Электронная почта не может быть пустой")
    String email;
    @Past(message = "Дата рождения не может быть в будущем")
    LocalDate birthday;

    Set<Long> friendsId = new HashSet<>();

    public void setFriendId(Long friendId) {
        friendsId.add(friendId);
    }

    public void removeFriendId(Long id) {
        friendsId.remove(id);
    }

}

