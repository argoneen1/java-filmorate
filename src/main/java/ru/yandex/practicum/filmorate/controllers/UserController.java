package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.exceptions.UserAlreadyExistException;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends SimpleController<User> {

    @Override
    protected IllegalArgumentException getExistException(int id) {
        return new UserAlreadyExistException( getElementName() + " with id " + id + " already was included.");
    }

    @Override
    protected String getElementName() {
        return "User";
    }
}
