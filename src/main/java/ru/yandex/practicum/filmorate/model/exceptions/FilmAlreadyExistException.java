package ru.yandex.practicum.filmorate.model.exceptions;

public class FilmAlreadyExistException extends IllegalArgumentException {
    public FilmAlreadyExistException(final String message) {
        super(message);
    }
}
