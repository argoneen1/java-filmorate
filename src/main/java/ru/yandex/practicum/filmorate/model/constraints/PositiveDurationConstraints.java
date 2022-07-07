package ru.yandex.practicum.filmorate.model.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = PositiveDurationValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD, PARAMETER})
public @interface PositiveDurationConstraints {
    String message() default "must be a positive duration." +
            " found: ${validatedValue}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
