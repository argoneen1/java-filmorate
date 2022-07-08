package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.IntId;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public abstract class SimpleController<T extends IntId> {

    Map<Integer, T> values = new HashMap<>();

    @Autowired
    Validator validator;

    @GetMapping
    public List<T> get() {
        return new ArrayList<>(values.values());
    }

    @PostMapping
    public T create(@Valid @RequestBody T value, BindingResult bindingResult) {
        for (ObjectError error :bindingResult.getAllErrors()) {
            log.warn(error.getDefaultMessage());
        }
        if (bindingResult.getAllErrors().size() > 0) {
            throw new IllegalArgumentException("Illegal " + getElementName().toLowerCase() + " fields state");
        }
        if (values.containsKey(value.getId())) {
            log.warn( getElementName() + " with id " + value.getId() + " already was included.");
            throw getExistException(value.getId());
        }
        values.put(value.getId(), value);
        return value;
    }

    @PutMapping
    public T update(@Valid @RequestBody T value, BindingResult bindingResult) {
        for (ObjectError error :bindingResult.getAllErrors()) {
            log.warn(error.getDefaultMessage());
        }
        if (bindingResult.getAllErrors().size() > 0) {
            throw new IllegalArgumentException("Illegal " + getElementName().toLowerCase() + " fields state");
        }
        if (!values.containsKey(value.getId()) ) {
            throw new IllegalArgumentException("There is no such " + getElementName().toLowerCase());
        }
        values.put(value.getId(), value);
        return value;
    }

    protected String getElementName() {
        throw new RuntimeException("this is abstract class");
    }

    protected IllegalArgumentException getExistException(int id) {
        throw new RuntimeException("this is abstract class");
    }
}
