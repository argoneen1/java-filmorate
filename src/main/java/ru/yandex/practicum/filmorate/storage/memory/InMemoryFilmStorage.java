package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> storage = new HashMap<>();
    private final Map<Long, Set<Long>> likesStoredByUsers = new HashMap<>();
    private final Map<Long, Set<Long>> likesStoredByFilms = new HashMap<>();

    @Override
    public List<Film> get() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Film> get(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Film create(Film elem) {
        if (storage.containsKey(elem.getId())) {
            throw new FilmAlreadyExistException("Film with id " + elem.getId() + " already exists", elem.getId());
        }
        storage.put(elem.getId(), elem);
        return elem;
    }

    @Override
    public Film update(Film elem) {
        if (!storage.containsKey(elem.getId()) ) {
            throw new NoSuchElementException("There is no such film");
        }
        storage.put(elem.getId(), elem);
        return elem;
    }

    public List<Film> getMostLiked(int count){
        return storage.values().stream()
                .sorted(Comparator.comparingInt(a -> -a.getLikeUserId().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public boolean setLike(long filmId, long userId) {
        Set<Long> likes = likesStoredByFilms.computeIfAbsent(filmId, k -> new HashSet<>());
        likes.add(userId);
        likes = likesStoredByUsers.computeIfAbsent(userId, k -> new HashSet<>());
        return likes.add(filmId);
    }

    @Override
    public boolean deleteLike(long filmId, long userId) {
        Set<Long> likes = likesStoredByFilms.get(filmId);
        if (likes != null) {
            likes.remove(userId);
        }
        likes = likesStoredByUsers.get(userId);
        if (likes != null) {
            return likes.remove(filmId);
        }
        return false;
    }
}
