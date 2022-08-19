package ru.yandex.practicum.filmorate.model.exceptions;

import lombok.Getter;

@Getter
public class ElementAlreadyExistException extends IllegalArgumentException{
    final String elemName;
    final long id;

    public ElementAlreadyExistException(String message, String elemName, long id) {
        super(message);
        this.elemName = elemName;
        this.id = id;
    }
}
