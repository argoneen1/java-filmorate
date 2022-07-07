package ru.yandex.practicum.filmorate.model.exceptions;

public class UserAlreadyExistException extends IllegalArgumentException {
    public UserAlreadyExistException(final String message) {
        super(message);
    }
}
