package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.constraints.DateConstraint;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {

    static int numberOfCreated = 1;
    private int id;
    @NotBlank @Email
    private String email;
    @Pattern(regexp = "^\\S+$")
    private String login;
    private String name;
    @DateConstraint(min = "1904-02-10", max = "now")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        id = numberOfCreated++;
        this.email = email;
        this.login = login;
        this.name = name == null || name.equals("") ? login : name;
        this.birthday = birthday;
        if (login.equals("common"))
            System.out.println(name);
    }
}
