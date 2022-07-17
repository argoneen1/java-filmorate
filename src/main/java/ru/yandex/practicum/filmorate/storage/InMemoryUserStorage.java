package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.FilmAlreadyExistException;

import java.util.*;

@Slf4j
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
            log.warn( User.getElemName() + " with id " + elem.getId() + " already was included.");
            throw new FilmAlreadyExistException("User with id " + elem.getId() + " already exists");
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
}
