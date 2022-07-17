package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.BaseModel;
import ru.yandex.practicum.filmorate.storage.BasicStorage;

import java.util.List;
import java.util.NoSuchElementException;

import static ru.yandex.practicum.filmorate.model.exceptions.Messages.getNoSuchElemMessage;

public class BasicService<T extends BaseModel, U extends BasicStorage<T>> {

    protected U storage;

    protected BasicService(U storage) {
        this.storage = storage;
    }
    public List<T> get() {
        return storage.get();
    }

    public T get(long id) {
        if (storage.get(id) == null)
            throw new NoSuchElementException(getNoSuchElemMessage(id, T.getElemName()));
        return storage.get(id);
    }

    public T create(T elem) {
        return storage.create(elem);
    }

    public T update(T elem) {
        return storage.update(elem);
    }

}
