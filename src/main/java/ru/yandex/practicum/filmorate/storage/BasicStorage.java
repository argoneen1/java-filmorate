package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import java.util.Optional;

public interface BasicStorage<T> {
    List<T> get();
    Optional<T> get(long id);
    T create(T elem);
    T update(T elem);
}
