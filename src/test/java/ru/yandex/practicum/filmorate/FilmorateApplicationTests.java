package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ReleaseDataException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {

	private FilmController filmController;
	private UserController userController;

	@BeforeEach
	void setUp() {
		filmController = new FilmController();
		userController = new UserController();
	}

	@Test
	void createFilmWithEarlyReleaseDate() {
		Film film = new Film(1L, "filmName", "Description",
				LocalDate.of(1985, 12, 27), 120);
		assertThrows(ReleaseDataException.class, () -> filmController.create(film),
				"Дата релиза не должна быть раньше 28 декабря 1895 года");
	}

	@Test
	void createFilmWithCorrectReleaseDate() {
		Film film = new Film(1L, "filmName", "Description",
				LocalDate.of(1985, 12, 29), 120);
		Film createdFilm = filmController.create(film);
		assertEquals(film.getName(), createdFilm.getName());
		List<Film> films = filmController.getAll();
		assertEquals(1, films.size(), "Должен быть 1 фильм в списке");
	}

	@Test
	void updateNonExistentFilm() {
		Film film = new Film(1L, "filmName", "Description",
				LocalDate.of(1985, 12, 29), 120);
		filmController.create(film);
		Film nonExistentFilm = new Film(999L, "filmName", "Description",
				LocalDate.of(2000, 1, 1), 100);

		assertThrows(FilmNotFoundException.class,
				() -> filmController.update(nonExistentFilm),
				"Должно быть выброшено FilmNotFoundException");
	}

	@Test
	void createUser() {
		User user = new User(1L, "userName", "Description",
				"user@yandex.ru", LocalDate.of(2000, 1, 1));
		User createdUser = userController.create(user);
		assertEquals(user.getName(), createdUser.getName());
		List<User> users = userController.getAll();
		assertEquals(1, users.size(), "Должен быть 1 пользователь в списке");
	}

	@Test
	void updateNonExistentUser() {
		User user = new User(1L, "userName", "Description",
				"user@yandex.ru", LocalDate.of(2000, 1, 1));
		userController.create(user);
		User nonExistentUser = new User(555L, "userName", "Description",
				"user@yandex.ru", LocalDate.of(2000, 1, 1));
		userController.create(user);

		assertThrows(UserNotFoundException.class,
				() -> userController.update(nonExistentUser),
				"Должно быть выброшено UserNotFoundException");
	}
}
