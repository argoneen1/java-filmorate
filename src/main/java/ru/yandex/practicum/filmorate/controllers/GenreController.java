package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.GenreService;


@Slf4j
@RestController
@RequestMapping("genres")
public class GenreController extends EnumController<Film.Genre, GenreService> {
    public GenreController(GenreService service) {
        super(service);
    }
}
