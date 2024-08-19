package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User newUser) {
        return userRepository.create(newUser);
    }

    @Override
    public User update(User newUser) {
        final User user = userRepository.findById(newUser.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь с " + newUser.getId() + "не найден"));
        return userRepository.update(newUser);
    }

    @Override
    public User findById(long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с " + id + "не найден"));
        return user;
    }

    @Override
    public List<User> getFriends(long id) {

        return userRepository.getFriends(findById(id));
    }

    @Override
    public void addFriend(long id, long friendId) {
        userRepository.addFriend(findById(id), findById(friendId));
    }

    @Override
    public void deleteFriend(long id, long friendId) {
        userRepository.deleteFriend(findById(id), findById(friendId));
    }

    @Override
    public List<User> getMutualFriends(long id, long otherId) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с " + id + "не найден"));
        final User userOther = userRepository.findById(otherId)
                .orElseThrow(() -> new NotFoundException("Пользователь с " + otherId + "не найден"));
        return userRepository.getMutualFriends(user, userOther);
    }
}
