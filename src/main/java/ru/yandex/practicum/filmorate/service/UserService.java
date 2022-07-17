package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.model.exceptions.Messages.getNoSuchElemMessage;

@Service
public class UserService extends BasicService<User, UserStorage> {

    @Autowired
    protected UserService(UserStorage storage) {
        super(storage);
    }

    public Set<User> getCommonFriends(long userId, long otherId) {
        validateId(List.of(userId, otherId));
        User user = storage.get(userId);
        User other = storage.get(otherId);
        return user.getFriends().stream()
                .filter(a -> other.getFriends().contains(a))
                .map(a -> storage.get(a))
                .collect(Collectors.toSet());
    }

    public Set<User> getFriends(long id) {
        validateIsUsersExist(Set.of(id));
        return storage.get(id).getFriends().stream()
                .map(a -> storage.get(a))
                .collect(Collectors.toSet());
    }

    public boolean addToFriends(long userId, long otherId) {
        validateId(List.of(userId, otherId));
        return storage.get(userId).getFriends().add(otherId) &&
                storage.get(otherId).getFriends().add(userId);
    }

    public boolean deleteFromFriends(long userId, long otherId) {
        validateId(List.of(userId, otherId));
        return storage.get(userId).getFriends().remove(otherId) &&
                storage.get(otherId).getFriends().remove(userId);
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
