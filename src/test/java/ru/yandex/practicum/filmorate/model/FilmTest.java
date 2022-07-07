package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

public class FilmTest {

    ValidatorFactory factory;
    Validator validator;
    Set<ConstraintViolation<Film>> violations;
    final String validName = "qwe";
    final String validDescr = "asd";
    final LocalDate validReleaseDate = LocalDate.of(2000, 10, 10);
    final Duration validDur = Duration.ofHours(1);
    final Film validFilm = new Film(validName, validDescr, validReleaseDate, validDur);

    final String invalidDescr = "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.";
    @BeforeEach
    void initialize() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validFilmTest() {
        violations = validator.validate(validFilm);
        assertTrue(violations.isEmpty());
    }

    @Test
    void filmNameValidationFail() {
        final Film invalidFilm = new Film("", validDescr, validReleaseDate, validDur);
        violations = validator.validate(invalidFilm);
        assertFalse(violations.isEmpty());
    }

    @Test
    void filmDescrValidationFail() {
        final Film invalidFilm = new Film(validName, invalidDescr, validReleaseDate, validDur);
        violations = validator.validate(invalidFilm);
        assertFalse(violations.isEmpty());
    }

    @Test
    void filmReleaseDateValidationFail() {
        final Film invalidFilm = new Film(validName, validDescr, LocalDate.MIN, validDur);
        violations = validator.validate(invalidFilm);
        assertFalse(violations.isEmpty());
    }

    @Test
    void filmDurationValidationFail() {
        final Film invalidFilm = new Film(validName, validDescr, validReleaseDate, Duration.ofHours(-1));
        violations = validator.validate(invalidFilm);
        assertFalse(violations.isEmpty());
    }
}
