package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.yandex.practicum.filmorate.model.constraints.DateConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class User extends BaseModel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private static long numberOfCreated = 1;
    @NotBlank @Email
    private String email;
    @Pattern(regexp = "^\\S+$")
    private String login;
    private String name;
    @DateConstraint(min = "1904-02-10", max = "now")
    private LocalDate birthday;
    @Setter(AccessLevel.NONE)
    private final Set<Long> friends;
    @Setter(AccessLevel.NONE)
    private final Set<Long> likedFilms;

    public User(@JsonProperty("email") String email,
                @JsonProperty("login") String login,
                @JsonProperty("name") String name,
                @JsonProperty("birthday") LocalDate birthday) {
        super();
        this.likedFilms = new HashSet<>();
        this.setId(numberOfCreated++);
        this.email = email;
        this.login = login;
        this.name = name == null || name.equals("") ? login : name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

    public User(@JsonProperty("id") long id,
                @JsonProperty("email") String email,
                @JsonProperty("login") String login,
                @JsonProperty("name") String name,
                @JsonProperty("birthday") LocalDate birthday) {
        super();
        setId(id);
        this.likedFilms = new HashSet<>();
        this.email = email;
        this.login = login;
        this.name = name == null || name.equals("") ? login : name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

    public boolean likeFilm(long filmId) {
        return likedFilms.add(filmId);
    }

    public boolean likeFilm(Collection<Long> films) {
        return likedFilms.addAll(films);
    }

    public boolean deleteFilmLike(long filmId) {
        return likedFilms.remove(filmId);
    }

    public boolean addToFriends(long friendId) {
        return friends.add(friendId);
    }

    public boolean addToFriends(Collection<Long> friends) {
        return this.friends.addAll(friends);
    }
    public boolean deleteFromFriends(long friendId) {
        return friends.remove(friendId);
    }

    public static String getElemName() {
        return "User";
    }

    @Override
    public void setPreviousIdGeneratorValue() {
        numberOfCreated--;
    }
}
