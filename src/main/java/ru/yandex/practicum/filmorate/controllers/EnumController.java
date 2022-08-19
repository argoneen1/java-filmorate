package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.service.EnumService;
import ru.yandex.practicum.filmorate.storage.EnumStorage;

import java.util.EnumSet;

@Slf4j
public class EnumController<T extends Enum<T>, U extends EnumService<T, ? extends EnumStorage<T>>>{

    private final U service;

    public EnumController(U service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public T get(@PathVariable long id) {
        return service.get(id);
    }

    @GetMapping
    public EnumSet<T> get() {
        return service.get();
    }
}
