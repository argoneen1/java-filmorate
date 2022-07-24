package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.NoSuchElementException;

import static ru.yandex.practicum.filmorate.model.exceptions.Messages.getNoSuchElemMessage;

@Service
public class FilmService extends BasicService<Film, FilmStorage> {

    @Autowired
    UserService userService;

    @Autowired
    protected FilmService(FilmStorage storage) {
        super(storage);
    }

    public boolean setLike(long filmId, long userId) {
        validate(filmId, userId);
        storage.get(filmId).getLikeUserId().add(userId);
        return userService.get(userId).likeFilm(filmId);
    }

    public boolean deleteLike(long filmId, long userId) {
        validate(filmId, userId);
        storage.get(filmId).getLikeUserId().remove(userId);
        return userService.get(userId).deleteFilmLike(filmId);
    }

    public List<Film> getMostLiked(int count) {
        return storage.getMostLiked(count);
    }

    private void validate(long filmId, long userId) {
        if (storage.get(filmId) == null)
            throw new NoSuchElementException(getNoSuchElemMessage(filmId, Film.getElemName()));
        if (userService.get(userId) == null)
            throw new NoSuchElementException(getNoSuchElemMessage(userId, User.getElemName()));
    }
}
