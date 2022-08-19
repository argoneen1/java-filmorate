package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.model.exceptions.ElementAlreadyExistException;
import ru.yandex.practicum.filmorate.model.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.model.exceptions.UserAlreadyExistException;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse noSuchElemHandler(NoSuchElementException e) {
        log.warn("no element, {}", e.getMessage());
        return new ErrorResponse("There is no such element", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgHandler(IllegalArgumentException e) {
        log.warn("illegal arguments, {}", e.getMessage());
        return new ErrorResponse("illegal arguments", e.getMessage());
    }

    @ExceptionHandler({FilmAlreadyExistException.class, UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse alreadyExistsHandler(ElementAlreadyExistException e) {
        log.warn("{} with id {} already exists", e.getElemName(), e.getId());
        return new ErrorResponse("already exists", e.getMessage());
    }
}
