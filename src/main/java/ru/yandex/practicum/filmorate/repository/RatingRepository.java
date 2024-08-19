package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RatingRepository {
    public Optional<Rating> findById(Long id);

    Collection<Rating> findAll();

    Rating create(Rating rating);

    void delete(Rating rating);

    Rating update(Rating rating);
}
