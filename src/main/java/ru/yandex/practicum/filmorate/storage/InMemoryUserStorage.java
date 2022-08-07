package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserAlreadyExistException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage{

    private final Map<Long, User> storage = new HashMap<>();
    @Override
    public List<User> get() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public User get(long id) {
        User user = storage.get(id);
        if (user == null) {
            throw new NoSuchElementException();
        }
        return user;
    }

    @Override
    public User create(User elem) {
        if (storage.containsKey(elem.getId())) {
            throw new UserAlreadyExistException("User with id " + elem.getId() + " already exists", elem.getId());
        }
        storage.put(elem.getId(), elem);
        return elem;
    }

    @Override
    public User update(User elem) {
        if (!storage.containsKey(elem.getId()) ) {
            throw new NoSuchElementException("There is no such user");
        }
        storage.put(elem.getId(), elem);
        return elem;
    }

    @Override
    public Set<User> getCommonFriends(long userId, long otherId) {
        User user = storage.get(userId);
        User other = storage.get(otherId);
        return user.getFriends().keySet().stream()
                .filter(a -> {
                    Boolean isConfirmed = other.getFriends().get(a);
                    if (isConfirmed == null)
                        return false;
                    return isConfirmed;
                })
                .map(storage::get)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean addToFriends(long userId, long otherId) {
        return Boolean.TRUE.equals(storage.get(userId).getFriends().put(otherId, true)) &&
                Boolean.TRUE.equals(storage.get(otherId).getFriends().put(userId, true));
    }

    @Override
    public Set<User> getFriends(long id) {
        return storage.get(id).getFriends().keySet().stream()
                .map(storage::get)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean deleteFromFriends(long userId, long otherId) {
        return storage.get(userId).getFriends().remove(otherId) &&
                storage.get(otherId).getFriends().remove(userId);
    }
}