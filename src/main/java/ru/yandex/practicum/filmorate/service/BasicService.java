package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.BaseModel;
import ru.yandex.practicum.filmorate.model.exceptions.Messages;
import ru.yandex.practicum.filmorate.storage.BasicStorage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public abstract class BasicService<T extends BaseModel, U extends BasicStorage<T>> {

    protected U storage;

    protected BasicService(U storage) {
        this.storage = storage;
    }
    public List<T> get() {
        return storage.get();
    }

    public Optional<T> get(long id) {
        if (storage.get(id).isEmpty())
            throw new NoSuchElementException(Messages.getNoSuchElemMessage(id, getElemClass()));
        return storage.get(id);
    }

    public T create(T elem) {
        return storage.create(elem);
    }

    public T update(T elem) {
        return storage.update(elem);
    }

    protected abstract Class<T> getElemClass();
}
