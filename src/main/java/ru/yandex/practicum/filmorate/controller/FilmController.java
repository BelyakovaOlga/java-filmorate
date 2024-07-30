package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping("/films")
    public Collection<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("/films/popular")
    public Collection<Film> findPopular(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.findPopular(count);
    }

    @PostMapping("/films")
    public Film create(@RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping("/films")
    public Film update(@RequestBody Film newFilm) {
        return filmService.update(newFilm);
    }

    @GetMapping("/films/{id}")
    public Film findFilm(@PathVariable long id) {
        return filmService.findById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void setLike(@PathVariable long id, @PathVariable long userId) {
        filmService.setLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLink(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLike(id, userId);
    }
}
