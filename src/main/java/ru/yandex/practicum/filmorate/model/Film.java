package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.model.constraints.DateConstraint;
import ru.yandex.practicum.filmorate.model.constraints.PositiveDurationConstraints;
import ru.yandex.practicum.filmorate.model.serialization.FilmDurationDeserializer;
import ru.yandex.practicum.filmorate.model.serialization.FilmDurationSerializer;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
public class Film extends BaseModel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static int numberOfCreated = 1;
    @NotBlank
    private String name;
    @Length(max = 200)
    private String description;
    @DateConstraint(min = "1895-12-28")
    private LocalDate releaseDate;
    @JsonSerialize  (using = FilmDurationSerializer.class)
    @PositiveDurationConstraints
    private Duration duration;
    @Setter(AccessLevel.NONE)
    private Set<Long> likeUserId;

    public Film(String name,
                String description,
                LocalDate releaseDate,
                @JsonDeserialize(using = FilmDurationDeserializer.class) Duration duration,
                Set<Long> likeUserId) {
        super();
        this.setId(numberOfCreated++);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likeUserId = likeUserId;
    }

    public Film(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("releaseDate") LocalDate releaseDate,
                @JsonProperty("duration")
                @JsonDeserialize(using = FilmDurationDeserializer.class) Duration duration) {
        super();
        this.setId(numberOfCreated++);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likeUserId = new HashSet<>();
    }

    public static String getElemName() {
        return "Film";
    }

    @Override
    public void setPreviousIdGeneratorValue() {
        numberOfCreated--;
    }
}
