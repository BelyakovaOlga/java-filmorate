package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Film {
    Long id;
    String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
    Integer likes;
    LinkedHashSet<Genre> genres;
    Rating mpa;
}

