package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    public Optional<Film> findById(Long id);

    Film create(Film data);

    Film update(Film data);

    List<Film> findAll();

    void setLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    Collection<Film> findPopular(int count);
}
