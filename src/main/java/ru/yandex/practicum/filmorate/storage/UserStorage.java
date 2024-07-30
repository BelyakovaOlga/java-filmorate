package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public interface UserStorage {

    public User create(User user);

    public User update(User newUser);

    public Optional<User> findById(long id);

    Collection<User> findAll();

    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<User> getFriends(User user);

    List<User> getMutualFriends(User user, User otherUser);
}

