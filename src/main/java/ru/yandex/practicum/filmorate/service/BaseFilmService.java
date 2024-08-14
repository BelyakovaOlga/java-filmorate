package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BaseFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage usertorage;

    @Override
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Collection<Film> findPopular(int count) {
        return filmStorage.findPopular(count);
    }

    @Override
    public Film create(Film filmNew) {
        return filmStorage.create(filmNew);
    }

    @Override
    public Film update(Film filmNew) {
        return filmStorage.update(filmNew);
    }

    @Override
    public Film findById(long id) {
        final Film film = filmStorage.findById(id)
                .orElseThrow(() -> new NotFoundException("Фильм с " + id + "не найден"));

        return film;
    }

    @Override
    public void setLike(long filmId, long userId) {
        User user = usertorage.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь с " + userId + "не найден"));
        filmStorage.setLike(findById(filmId), user);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        User user = usertorage.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь с " + userId + "не найден"));
        filmStorage.deleteLike(findById(filmId), user);
    }
}

