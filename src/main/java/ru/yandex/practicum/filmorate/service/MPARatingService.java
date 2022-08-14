package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.db.MPARatingDbStorage;

@Service
public class MPARatingService extends EnumService<Film.MPARating, MPARatingDbStorage> {
    protected MPARatingService(MPARatingDbStorage storage) {
        super(storage);
    }
}
