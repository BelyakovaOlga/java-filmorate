package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    static final String genrePath = "/mpa";

    @GetMapping(genrePath)
    public Collection<Rating> findAll() {
        return ratingService.findAll();
    }

    @GetMapping("/mpa/{id}")
    public Rating findUser(@PathVariable long id) {
        return ratingService.findById(id);
    }
}
