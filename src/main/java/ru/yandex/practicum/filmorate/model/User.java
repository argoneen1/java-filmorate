package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.model.constraints.DateConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseModel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static int numberOfCreated = 1;
    @NotBlank @Email
    private String email;
    @Pattern(regexp = "^\\S+$")
    private String login;
    private String name;
    @DateConstraint(min = "1904-02-10", max = "now")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        super();
        this.setId(numberOfCreated++);
        this.email = email;
        this.login = login;
        this.name = name == null || name.equals("") ? login : name;
        this.birthday = birthday;
        if (login.equals("common"))
            System.out.println(name);
    }
}
