package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

@DisplayName("Пользователь")
public class UserTest {

    @Test
    @DisplayName("Должен создать пользователя")
    void shouldCreatUser() {
        User userNew = User.builder()
                .email("olga@.mail.ru")
                .login("LOlga")
                .name("Olga")
                .build();
        UserController userController = new UserController();
        User userCreate = userController.create(userNew);
        Assertions.assertNotNull(userCreate, "Пользователь не создан");
    }

    @Test
    @DisplayName("Должен вернуть ошибку некорректного Email")
    void shouldFailEmail() {
        User userNew = User.builder()
                .email("olga*.mail.ru")
                .login("LOlga")
                .name("Olga")
                .build();
        UserController userController = new UserController();
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.create(userNew);
        }, "Исключения об некорректном Email нет");
    }

    @Test
    @DisplayName("Должен вернуть ошибку пустого Email")
    void shouldFailEmailNull() {
        User userNew = User.builder()
                .login("LOlga")
                .name("Olga")
                .build();
        UserController userController = new UserController();
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.create(userNew);
        }, "Исключения об пустом Email нет");
    }

    @Test
    @DisplayName("Должен вернуть ошибку наличия пробелов в Login")
    void shouldFailLogin() {
        User userNew = User.builder()
                .email("olga@.mail.ru")
                .login("L Olga")
                .name("Olga")
                .build();
        UserController userController = new UserController();
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.create(userNew);
        }, "Исключения наличия пробелов в Login нет");
    }

    @Test
    @DisplayName("Должен вернуть ошибку некорректоного Birthday")
    void shouldFailBirthday() {
        User userNew = User.builder()
                .email("olga@.mail.ru")
                .login("LOlga")
                .name("Olga")
                .birthday(LocalDate.of(2025, 12, 12))
                .build();
        UserController userController = new UserController();
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.create(userNew);
        }, "Исключения о некорректном Дне Рождения нет");
    }

    @Test
    @DisplayName("Должен использовать логин пользователя, если имя не задано")
    void shouldUseLoginIfNameEmty() {
        User userNew = User.builder()
                .email("olga@.mail.ru")
                .login("LOlga")
                .build();
        UserController userController = new UserController();
        User userCreate = userController.create(userNew);
        Assertions.assertEquals(userCreate.getName(), userCreate.getLogin(), "Имя не равно логину");
    }
}
