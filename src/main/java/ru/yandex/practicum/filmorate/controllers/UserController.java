package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserAlreadyExistException;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Validator validator;

    Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> get() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult bindingResult) {
        for (ObjectError error :bindingResult.getAllErrors()) {
            log.warn(error.getDefaultMessage());
        }
        if (bindingResult.getAllErrors().size() > 0) {
            System.out.println(bindingResult.getAllErrors().size() + "XXX"+user);
            throw new IllegalArgumentException("Illegal user`s fields state");
        }
        if (users.containsKey(user.getId())) {
            log.warn("User " + user.getName() + " already exists.");
            throw new UserAlreadyExistException("User " + user.getName() + " already exists.");
        }
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user, BindingResult bindingResult) {
        for (ObjectError error :bindingResult.getAllErrors()) {
            log.warn(error.getDefaultMessage());
        }
        if (bindingResult.getAllErrors().size() > 0) {
            System.out.println(user);
            throw new IllegalArgumentException("Illegal user`s fields state");
        }
        if (!users.containsKey(user.getId())) {
            throw new IllegalArgumentException("There is no such user");
        }
        users.replace(user.getId(), user);
        return user;
    }
}
