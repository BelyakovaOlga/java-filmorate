package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Service
public interface FilmService {
    Film create(Film filmNew);

    Film update(Film filmNew);

    Film findById(long id);

    Collection<Film> findAll();

    Collection<Film> findPopular(int count);

    void setLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);
}

