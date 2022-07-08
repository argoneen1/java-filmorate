package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.model.constraints.DateConstraint;
import ru.yandex.practicum.filmorate.model.constraints.PositiveDurationConstraints;
import ru.yandex.practicum.filmorate.model.serialization.FilmDurationDeserializer;
import ru.yandex.practicum.filmorate.model.serialization.FilmDurationSerializer;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.LocalDate;


@Data
public class Film implements IntId{

    private static int numberOfCreated = 1;
    private int id;
    @NotBlank
    private String name;
    @Length(max = 200)
    private String description;
    @DateConstraint(min = "1895-12-28")
    private LocalDate releaseDate;
    @JsonSerialize  (using = FilmDurationSerializer.class)
    @PositiveDurationConstraints
    private Duration duration;

    public Film(String name,
                String description,
                LocalDate releaseDate,
                @JsonDeserialize(using = FilmDurationDeserializer.class) Duration duration) {
        id = numberOfCreated++;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
