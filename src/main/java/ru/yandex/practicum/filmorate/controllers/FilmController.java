package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.Controller;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.FilmAlreadyExistException;

import javax.validation.Valid;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private Validator validator;

    Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> get() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film, BindingResult bindingResult) {
        for (ObjectError error :bindingResult.getAllErrors()) {
            log.warn(error.getDefaultMessage());
        }
        if (bindingResult.getAllErrors().size() > 0) {
            throw new IllegalArgumentException("Illegal film`s fields state");
        }
        Map<LocalDate, Film> map;
        if (films.containsKey(film.getId())) {
            log.warn("Film " + film.getName() + " already was included.");
            throw new FilmAlreadyExistException("Film " + film.getName() + " already was included.");
        }
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film, BindingResult bindingResult) {
        for (ObjectError error :bindingResult.getAllErrors()) {
            log.warn(error.getDefaultMessage());
        }
        if (bindingResult.getAllErrors().size() > 0) {
            throw new IllegalArgumentException("Illegal film`s fields state");
        }
        if (!films.containsKey(film.getId()) ) {
            throw new IllegalArgumentException("There is no such film");
        }
        films.put(film.getId(), film);
        return film;
    }
}
