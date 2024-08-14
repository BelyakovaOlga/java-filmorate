package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

@Service
public interface UserService {
    Collection<User> findAll();

    User create(User newUser);

    User update(User newUser);

    User findById(long id);

    void addFriend(long id, long friendId);

    void deleteFriend(long id, long friendId);

    List<User> getFriends(long id);

    List<User> getMutualFriends(long id, long otherId);
}

