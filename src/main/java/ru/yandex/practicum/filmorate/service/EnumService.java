package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.exceptions.Messages;
import ru.yandex.practicum.filmorate.storage.EnumStorage;

import java.util.EnumSet;
import java.util.NoSuchElementException;

public class EnumService<T extends Enum<T>, U extends EnumStorage<T>> {

    protected U storage;

    protected EnumService(U storage) {
        this.storage = storage;
    }

    public EnumSet<T> get() {
        return storage.get();
    }

    public T get(long id) {
        return storage.get(id).orElseThrow(() -> new NoSuchElementException(Messages.getNoSuchElemMessage(id)));
    }
}
