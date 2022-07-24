package ru.yandex.practicum.filmorate.model.exceptions;

import ru.yandex.practicum.filmorate.model.User;

public class UserAlreadyExistException extends ElementAlreadyExistException {
    public UserAlreadyExistException(final String message, long id) {
        super(message, User.getElemName(), id);
    }
}
