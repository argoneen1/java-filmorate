package ru.yandex.practicum.filmorate.model.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = DateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD, PARAMETER})
public @interface DateConstraint {


    String min() default "-999999999-01-01"; //LocalDate.MIN.toEpochDay()

    String max() default "+999999999-12-31"; //LocalDate.MAX.toEpochDay()

    String message() default "must be a valid date range." +
            " found: ${validatedValue}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}