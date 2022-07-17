package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("films")
public class FilmController extends BasicController<Film, FilmService> {

    @Autowired
    public FilmController(FilmService service) {
        super(service);
    }

    @PutMapping("/{id}/like/{userId}")
    public boolean setLike(@PathVariable(name = "id") Long filmId, // return false if like is already settled
                           @PathVariable(name = "userId") Long userId) {
        return service.setLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public boolean deleteLike(@PathVariable(name = "id")     Long filmId, // return false if like is already settled
                              @PathVariable(name = "userId") Long userId) {
        return service.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostLikedFilms(@RequestParam(defaultValue = "10") int count) {
        if (count <= 0)
            throw new IllegalArgumentException("number of films must be positive");
        return service.getMostLiked(count);
    }

}
