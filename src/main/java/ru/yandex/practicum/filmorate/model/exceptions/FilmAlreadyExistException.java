package ru.yandex.practicum.filmorate.model.exceptions;

import ru.yandex.practicum.filmorate.model.Film;

public class FilmAlreadyExistException extends ElementAlreadyExistException {

    public FilmAlreadyExistException(final String message, final long id) {
        super(message, Film.getElemName(), id);
    }
}
