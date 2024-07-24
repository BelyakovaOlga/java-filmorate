package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private final LocalDate birthdayOfCinema = LocalDate.of(1895, 12, 28);

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    private void validateDataOfFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Пустое значение name недопустимо");
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Длина описания превышает 200 символов");
            throw new ValidationException("Максимальная длина описания превышает 200 символов");
        }
        if (film.getReleaseDate().isBefore(birthdayOfCinema)) {
            log.warn("Дата релиза ранее появления кинематографа (28.12.1895)");
            throw new ValidationException("Некорректная дата релиза, дата релиза не может быть меньше даты 28.12.1895");
        }
        if (film.getDuration() < 0) {
            log.warn("Продолжительность фильма меньше 0");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }

    @PostMapping
    public Film create(@RequestBody Film film) {

        validateDataOfFilm(film);

        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм {} добавлне", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        validateDataOfFilm(newFilm);

        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());

            oldFilm.setName(newFilm.getName());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
            log.info("Фильм {} обновлен", oldFilm.getName());
            return oldFilm;
        }
        log.warn("Обновление фильма завершилось с ошибкой, фильма с id = {} нет", newFilm.getId());
        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
