package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.NoSuchElementException;

import static ru.yandex.practicum.filmorate.model.exceptions.Messages.getNoSuchElemMessage;

@Service
public class FilmService extends BasicService<Film, FilmStorage> {
    @Override
    protected Class<Film> getElemClass() {
        return Film.class;
    }

    final UserService userService;

    @Autowired
    protected FilmService(
            @Qualifier("FilmDbStorage") FilmStorage storage,
            UserService userService) {
        super(storage);
        this.userService = userService;
    }

    public boolean setLike(long filmId, long userId) {
        validate(filmId, userId);
        return storage.setLike(filmId, userId);
    }

    public boolean deleteLike(long filmId, long userId) {
        validate(filmId, userId);
        return storage.deleteLike(filmId, userId);
    }

    public List<Film> getMostLiked(int count) {
        return storage.getMostLiked(count);
    }

    private void validate(long filmId, long userId) {
        if (storage.get(filmId).isEmpty())
            throw new NoSuchElementException(getNoSuchElemMessage(filmId, Film.class));
        if (userService.get(userId).isEmpty())
            throw new NoSuchElementException(getNoSuchElemMessage(userId, User.class));
    }
}
