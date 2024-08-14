package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    HashMap<Long, Set<Long>> usersLike = new HashMap<>();
    private static long globalId = 1;
    private final LocalDate birthdayOfCinema = LocalDate.of(1895, 12, 28);

    public Collection<Film> findAll() {
        return films.values();

    }

    public Collection<Film> findPopular(int count) {
        return films.values().stream()
                .sorted((Film f1, Film f2) -> {
                    Integer compare = f1.getLikes().compareTo(f2.getLikes());

                    return compare * (-1);
                })
                .limit(count)
                .collect(Collectors.toList());
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

    @Override
    public Film create(Film film) {
        validateDataOfFilm(film);
        film.setId(getNextId());
        film.setLikes(0);
        films.put(film.getId(), film);
        log.info("Фильм {} добавлне", film.getName());
        return film;
    }

    @Override
    public Film update(Film newFilm) {
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

    private static long getNextId() {
        return globalId++;
    }

    public Optional<Film> findById(long id) {
        return Optional.ofNullable(films.get(id));
    }

    public void setLike(Film film, User user) {
        Set<Long> setUsersLike = usersLike.get(film.getId());
        Integer countLike = film.getLikes();

        if (countLike != null) {
            film.setLikes(countLike + 1);
        } else {
            film.setLikes(1);
        }

        if (setUsersLike == null) {
            setUsersLike = new HashSet<Long>();

        }
        setUsersLike.add(user.getId());
    }

    public void deleteLike(Film film, User user) {
        Set<Long> setUsersLike = usersLike.get(film.getId());

        if (film.getLikes() > 1) {
            film.setLikes(film.getLikes() - 1);
            if (setUsersLike.contains(user.getId())) {
                setUsersLike.remove(user.getId());
            }
        }
    }

    public int getNumLike(long id) {
        return usersLike.get(id).size();
    }
}