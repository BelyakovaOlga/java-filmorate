package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    HashMap<Long, Set<Long>> userFreindsId = new HashMap<>();
    private static long globalId = 1;

    private void validateDataOfUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.warn("Пустое значение Email недопустимо");
            throw new ValidationException("Имейл должен быть указан");
        }
        if (!user.getEmail().contains("@")) {
            log.warn("Email не содержит @");
            throw new ValidationException("Некорректный имейл");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.warn("Пустое значение Login недопустимо");
            throw new ValidationException("Логин должен быть указан");
        }
        if (user.getLogin().contains(" ")) {
            log.warn("Использование проблелов в поле Login недопустимо");
            throw new ValidationException("В логине присутсвуют неразрешенные символы (пробелы)");
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Поле Birthday не может быть больше текущей даты");
            throw new ValidationException("Некорректная дата рождения");
        }
    }

    @Override
    public User create(User user) {
        validateDataOfUser(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Добавден пользоваетль {}", user.getLogin());
        return user;
    }

    @Override
    public User update(User newUser) {

        User oldUser = findById(newUser.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь с " + newUser.getId() + "не найден"));

        oldUser.setLogin(newUser.getLogin());
        if (newUser.getName() != null && !newUser.getName().isBlank()) {
            oldUser.setName(newUser.getName());
        } else {
            oldUser.setName(newUser.getLogin());
        }
        oldUser.setEmail(newUser.getEmail());
        oldUser.setBirthday(newUser.getBirthday());
        log.info("Обновлен пользоваетль {}", oldUser.getLogin());
        return oldUser;
    }

    private static long getNextId() {
        return globalId++;
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void addFriend(User user, User friend) {

        Set<Long> uFriends = userFreindsId.computeIfAbsent(user.getId(), id -> new HashSet<>());
        uFriends.add(friend.getId());

        Set<Long> fFriends = userFreindsId.computeIfAbsent(friend.getId(), id -> new HashSet<>());
        fFriends.add(user.getId());

    }

    @Override

    public void deleteFriend(User user, User friend) {
        Set<Long> userFriends = userFreindsId.computeIfAbsent(user.getId(), id -> new HashSet<>());
        userFriends.remove(friend.getId());

        Set<Long> fFriends = userFreindsId.computeIfAbsent(friend.getId(), id -> new HashSet<>());
        fFriends.remove(user.getId());

    }

    @Override
    public List<User> getFriends(User user) {
        Set<Long> userFriends = userFreindsId.get(user.getId());
        List<User> listUser;

        if (userFriends != null) {
            listUser = userFriends.stream()
                    .map(friendId -> findById(friendId).get())
                    .collect(Collectors.toList());
        } else {
            listUser = new ArrayList<User>();
        }
        return listUser;
    }

    public List<User> getMutualFriends(User user, User otherUser) {
        Set<Long> result = new HashSet<>(userFreindsId.get(user.getId()));
        Set<Long> resultListOther = userFreindsId.get(otherUser.getId());
        List<User> listMutualFriends;
        if (result != null && resultListOther != null) {
            result.retainAll(resultListOther);

            listMutualFriends = result.stream()
                    .map(userId -> findById(userId).get())
                    .collect(Collectors.toList());
        } else {
            listMutualFriends = new ArrayList<User>();
        }

        return listMutualFriends;
    }
}
