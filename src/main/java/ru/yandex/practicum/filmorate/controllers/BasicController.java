package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.BaseModel;
import ru.yandex.practicum.filmorate.model.exceptions.Messages;
import ru.yandex.practicum.filmorate.service.BasicService;
import ru.yandex.practicum.filmorate.storage.BasicStorage;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
public abstract class BasicController<T extends BaseModel, U extends BasicService<T, ? extends BasicStorage<T>>> {

    protected final U service;

    @Autowired
    public BasicController(U service) {
        this.service = service;
    }
    @Autowired
    Validator validator;

    @GetMapping
    public List<T> get() {
        return service.get();
    }

    @GetMapping("/{id}")
    public T get(@PathVariable Integer id) {
        return service.get(id).orElseThrow(() -> new NoSuchElementException(Messages.getNoSuchElemMessage(id)));
    }

    @PostMapping
    public T create(@Valid @RequestBody T value, BindingResult bindingResult) {
        for (ObjectError error : bindingResult.getAllErrors()) {
            log.warn(error.getDefaultMessage());
        }
        if (!bindingResult.getAllErrors().isEmpty()) {
            value.setPreviousIdGeneratorValue();
            throw new IllegalArgumentException(Messages.ILLEGAL_FIELDS_STATE);
        }
        return service.create(value);
    }

    @PutMapping
    public T update(@Valid @RequestBody T value, BindingResult bindingResult) {
        for (ObjectError error : bindingResult.getAllErrors()) {
            log.warn(error.getDefaultMessage());
        }
        value.setPreviousIdGeneratorValue();
        if (!bindingResult.getAllErrors().isEmpty()) {
            throw new IllegalArgumentException(Messages.ILLEGAL_FIELDS_STATE);
        }
        return service.update(value);
    }
}
