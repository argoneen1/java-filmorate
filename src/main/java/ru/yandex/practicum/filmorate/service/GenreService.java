package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

@Service
public class GenreService extends EnumService<Film.Genre, GenreDbStorage> {
    protected GenreService(GenreDbStorage storage) {
        super(storage);
    }
}
