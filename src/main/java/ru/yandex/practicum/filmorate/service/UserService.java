package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static ru.yandex.practicum.filmorate.model.exceptions.Messages.getNoSuchElemMessage;

@Service
public class UserService extends BasicService<User, UserStorage> {

    @Autowired
    protected UserService(UserStorage storage) {
        super(storage);
    }

    public Set<User> getCommonFriends(long userId, long otherId) {
        validateId(List.of(userId, otherId));
        return storage.getCommonFriends(userId, otherId);
    }

    public Set<User> getFriends(long id) {
        validateIsUsersExist(Set.of(id));
        return storage.getFriends(id);
    }

    public boolean addToFriends(long userId, long otherId) {
        validateId(List.of(userId, otherId));
        return storage.addToFriends(userId, otherId);
    }

    public boolean deleteFromFriends(long userId, long otherId) {
        validateId(List.of(userId, otherId));
        return storage.deleteFromFriends(userId, otherId);
    }

    private void validateIsUsersExist(Set<Long> ids) {
        for (long id : ids) {
            if (storage.get(id) == null)
                throw new NoSuchElementException(getNoSuchElemMessage(id, User.getElemName()));
        }
    }

    private void validateId(List<Long> idList) {
        Set<Long> idSet = new HashSet<>(idList.size());
        for (long id : idList) {
            boolean isUnique = idSet.add(id);
            if (!isUnique)
                throw new IllegalArgumentException("User`s id must be unique");
        }
        validateIsUsersExist(idSet);
    }
}
