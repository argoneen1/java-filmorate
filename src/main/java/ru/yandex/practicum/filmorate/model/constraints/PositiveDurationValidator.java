package ru.yandex.practicum.filmorate.model.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class PositiveDurationValidator implements ConstraintValidator<PositiveDurationConstraints, Duration> {
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        return !duration.isNegative();
    }
}
