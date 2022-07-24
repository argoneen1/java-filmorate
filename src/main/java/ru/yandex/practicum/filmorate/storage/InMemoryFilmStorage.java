package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.exceptions.FilmAlreadyExistException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> storage = new HashMap<>();
    @Override
    public List<Film> get() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Film get(long id) {
        Film film = storage.get(id);
        if (film == null) {
            throw new NoSuchElementException();
        }
        return film;
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
}
