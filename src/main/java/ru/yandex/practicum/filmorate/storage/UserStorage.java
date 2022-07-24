package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;


public interface UserStorage extends BasicStorage<User> {
    Set<User> getCommonFriends(long userId, long otherId);

    Set<User> getFriends(long id);

    boolean addToFriends(long userId, long otherId);

    boolean deleteFromFriends(long userId, long otherId);
}
