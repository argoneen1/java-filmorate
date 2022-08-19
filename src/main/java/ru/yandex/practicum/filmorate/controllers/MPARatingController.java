package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.MPARatingService;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MPARatingController extends EnumController<Film.MPARating, MPARatingService> {
    public MPARatingController(MPARatingService service) {
        super(service);
    }
}
