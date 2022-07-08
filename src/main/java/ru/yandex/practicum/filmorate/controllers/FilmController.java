package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.FilmAlreadyExistException;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends SimpleController<Film> {

    @Override
    protected IllegalArgumentException getExistException(int id) {
        return new FilmAlreadyExistException( getElementName() + " with id " + id + " already was included.");
    }

    @Override
    protected String getElementName() {
        return "Film";
    }
}
