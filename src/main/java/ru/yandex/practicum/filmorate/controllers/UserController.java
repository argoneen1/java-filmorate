package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends BasicController<User, UserService> {

    @Autowired
    public UserController(UserService service) {
        super(service);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public boolean addToFriendsList(@PathVariable(name = "id")       Long userId,
                                    @PathVariable(name = "friendId") Long otherId) {
        return service.addToFriends(userId, otherId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public boolean deleteFromFriendsList(@PathVariable(name = "id")       Long userId,
                                         @PathVariable(name = "friendId") Long otherId) {
        return service.deleteFromFriends(userId, otherId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable(name = "id") String id) {
        return service.getFriends(Long.parseLong(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable(name = "id")      Long userId,
                                      @PathVariable(name = "otherId") Long otherId) {
        return service.getCommonFriends(userId, otherId);
    }
}


