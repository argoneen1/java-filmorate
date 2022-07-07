package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UserTest {

    ValidatorFactory factory;
    Validator validator;
    Set<ConstraintViolation<User>> violations;
    User user;
    final LocalDate validDate = LocalDate.of(2000, 10, 10);

    @BeforeEach
    void initialize() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    User validUser() {
        return new User("qwe@rty.com", "asd", "zxc", validDate);
    }

    @Test
    void correctUserValidationTest() {
        violations = validator.validate(validUser());
        assertTrue(violations.isEmpty());
    }

    @Test
    void userEmailValidationFail() {
        List<String> invalidEmails = List.of("", "@", "a", "q@", "@w");
        for (String invalidEmail : invalidEmails) {
            user = new User(invalidEmail, "qwe", "", validDate);
            violations = validator.validate(user);
            assertFalse(violations.isEmpty());
            validator = factory.getValidator();
        }
    }
    @Test
    void userLoginValidationFail() {
        List<String> invalidLogins = List.of("", "qw ", "qw rt", "qw\n", "\t");
        for (String invalidLogin : invalidLogins) {
            user = new User("qwe@rty.com", invalidLogin, "", validDate);
            violations = validator.validate(user);
            assertFalse(violations.isEmpty());
            validator = factory.getValidator();
        }
    }

    @Test
    void userNameTest() {
        assertEquals("zxc", validUser().getName());
        assertEquals("asd", (new User("qwe@rty", "asd", "", validDate).getName()));
    }

    @Test
    void userBirthDayFail() {
        List<LocalDate> invalidBirthdays = List.of(LocalDate.of(1900,1,1), LocalDate.of(2500,12,12));
        for (LocalDate invalidBirthday : invalidBirthdays) {
            user = new User("qwe@rty.com", "invalidLogin", "", invalidBirthday);
            violations = validator.validate(user);
            assertFalse(violations.isEmpty());
            validator = factory.getValidator();
        }
    }
}
