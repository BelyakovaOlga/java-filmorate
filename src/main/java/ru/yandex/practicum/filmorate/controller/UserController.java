package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    private void validateDataOfUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.warn("Пустое значение Email недопустимо");
            throw new ValidationException("Имейл должен быть указан");
        }
        if (!user.getEmail().contains("@")) {
            log.warn("Email не содержит @");
            throw new ValidationException("Некорректный имейл");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.warn("Пустое значение Login недопустимо");
            throw new ValidationException("Логин должен быть указан");
        }
        if (user.getLogin().contains(" ")) {
            log.warn("Использование проблелов в поле Login недопустимо");
            throw new ValidationException("В логине присутсвуют неразрешенные символы (пробелы)");
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Поле Birthday не может быть больше текущей даты");
            throw new ValidationException("Некорректная дата рождения");
        }
    }

    @PostMapping
    public User create(@RequestBody User user) {
        validateDataOfUser(user);

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Добавден пользоваетль {}", user.getLogin());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {

        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());

            oldUser.setLogin(newUser.getLogin());
            if (newUser.getName() != null && !newUser.getName().isBlank()) {
                oldUser.setName(newUser.getName());
            } else {
                oldUser.setName(newUser.getLogin());
            }
            oldUser.setEmail(newUser.getEmail());
            oldUser.setBirthday(newUser.getBirthday());
            log.info("Обновлен пользоваетль {}", oldUser.getLogin());
            return oldUser;
        }
        log.warn("Обновлен пользоваетля {} завершилось с ошибкой, такого пользователя нет", newUser.getLogin());
        throw new NotFoundException("Ползователь с id = " + newUser.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
