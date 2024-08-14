package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {
    private final UserStorage userStorage;

    @Override
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User create(User newUser) {
        return userStorage.create(newUser);
    }

    @Override
    public User update(User newUser) {
        return userStorage.update(newUser);
    }

    @Override
    public User findById(long id) {
        final User user = userStorage.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с " + id + "не найден"));
        return user;
    }

    @Override
    public List<User> getFriends(long id) {

        return userStorage.getFriends(findById(id));
    }

    @Override
    public void addFriend(long id, long friendId) {
        userStorage.addFriend(findById(id), findById(friendId));
    }

    @Override
    public void deleteFriend(long id, long friendId) {
        userStorage.deleteFriend(findById(id), findById(friendId));
    }

    @Override
    public List<User> getMutualFriends(long id, long otherId) {
        return userStorage.getMutualFriends(findById(id), findById(otherId));
    }
}
