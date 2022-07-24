package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;


public interface FilmStorage extends BasicStorage<Film> {
    List<Film> getMostLiked(int count);
}
