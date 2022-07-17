package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface BasicStorage<T> {

    List<T> get();
    T get(long id);
    T create(T elem);
    T update(T elem);
}
