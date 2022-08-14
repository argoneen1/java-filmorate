package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.model.constraints.DateConstraint;
import ru.yandex.practicum.filmorate.model.constraints.PositiveDurationConstraints;
import ru.yandex.practicum.filmorate.model.serialization.EnumSerializerWithName;
import ru.yandex.practicum.filmorate.model.serialization.GenreDeserializerIdOnly;
import ru.yandex.practicum.filmorate.model.serialization.MpaDeserializer;
import ru.yandex.practicum.filmorate.model.serialization.duration.FilmDurationDeserializer;
import ru.yandex.practicum.filmorate.model.serialization.duration.FilmDurationSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
public class Film extends BaseModel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
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
    private EnumSet<Genre> genres;
    //@JsonSerialize (using = EnumSerializerOnlyId.class)
    @JsonProperty("mpa")
    @NotNull
    private MPARating mpaRating;

    @JsonSerialize(using = EnumSerializerWithName.class)
    public enum MPARating implements StoredEnum{
        G("G"),
        PG("PG"),
        PG_13("PG-13"),
        R("R"),
        NC_17("NC-17");
        public static MPARating fromNumber(int number) {
            switch (number){
                case 1:
                    return G;
                case 2:
                    return PG;
                case 3:
                    return PG_13;
                case 4:
                    return R;
                case 5:
                    return NC_17;
                default:
                    throw new IllegalArgumentException("incorrect rating id");
            }
        }
        public final String localizedName;
        MPARating(String localizedName) {
            this.localizedName = localizedName;
        }
        @Override
        public String getLocalizedName() {
            return localizedName;
        }

        @Override
        public int getId() {
            return this.ordinal() + 1;
        }
    }

    @JsonSerialize(using = EnumSerializerWithName.class)
    @JsonDeserialize(using = GenreDeserializerIdOnly.class)
    public enum Genre implements StoredEnum{
        COMEDY("Комедия"),
        DRAMA("Драма"),
        CARTOON("Мультфильм"),
        THRILLER("Триллер"),
        DOCUMENTARY("Документальный"),
        ACTION("Боевик");
        public final String localizedName;
        Genre(String localizedName) {
            this.localizedName = localizedName;
        }
        public static Genre fromNumber(int number) {
            switch (number){
                case 1:
                    return COMEDY;
                case 2:
                    return DRAMA;
                case 3:
                    return CARTOON;
                case 4:
                    return THRILLER;
                case 5:
                    return DOCUMENTARY;
                case 6:
                    return ACTION;
                default:
                    throw new IllegalArgumentException("incorrect genre");
            }
        }

        @Override
        public String getLocalizedName() {
            return localizedName;
        }

        @Override
        public int getId() {
            return this.ordinal() + 1;
        }
    }
    @JsonCreator
    public Film(@JsonProperty("id") long id,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("releaseDate") LocalDate releaseDate,
                @JsonProperty("duration")
                @JsonDeserialize(using = FilmDurationDeserializer.class) Duration duration,
                @JsonProperty("mpa")
                @JsonDeserialize(using = MpaDeserializer.class) MPARating mpa,
                @JsonProperty("genres") EnumSet<Genre> genres) {
        super();
        this.setId(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likeUserId = new HashSet<>();
        this.mpaRating = mpa;
        this.genres = genres;
    }

    public Film(@JsonProperty("id") long id,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("releaseDate") LocalDate releaseDate,
                @JsonProperty("duration")
                @JsonDeserialize(using = FilmDurationDeserializer.class) Duration duration,
                @JsonProperty("mpa")
                @JsonDeserialize(using = MpaDeserializer.class) MPARating mpa) {
        super();
        this.setId(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likeUserId = new HashSet<>();
        this.mpaRating = mpa;
        this.genres = EnumSet.noneOf(Film.Genre.class);
    }
    public static String getElemName() {
        return "Film";
    }

    public boolean addGenre(Genre genre) {
        return genres.add(genre);
    }
    public boolean addGenre(Collection<Genre> genres) {
        return this.genres.addAll(genres);
    }
    @Override
    public void setPreviousIdGeneratorValue() {
        numberOfCreated--;
    }
}
