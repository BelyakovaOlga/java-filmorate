package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.ValidateService;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ValidateService validateService;
    static final String userPath = "/users";

    @GetMapping(userPath)
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @PostMapping(userPath)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        validateService.validateUser(user);
        return userService.create(user);
    }

    @PutMapping(userPath)
    public User update(@RequestBody User newUser) {
        validateService.validateUser(newUser);
        return userService.update(newUser);
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable long id) {
        return userService.findById(id);
    }

    @PutMapping("/users/{id}/friends/{friend-id}")
    public void addFriend(@PathVariable("id") long id, @PathVariable("friend-id") long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friend-id}")
    public void deleteFriend(@PathVariable long id, @PathVariable("friend-id") long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{other-id}")
    public List<User> getMutualFriends(@PathVariable long id, @PathVariable("other-id") long otherId) {
        return userService.getMutualFriends(id, otherId);
    }
}

