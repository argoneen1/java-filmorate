package ru.yandex.practicum.filmorate.model.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateValidator implements ConstraintValidator<DateConstraint, LocalDate> {

    private LocalDate minDate;
    private LocalDate maxDate;

    @Override
    public void initialize(DateConstraint constraintAnnotation) {
        minDate = getLocalDate(constraintAnnotation.min());
        maxDate = getLocalDate(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null)
            return false;
        return localDate.isAfter(minDate) && localDate.isBefore(maxDate);
    }

    private LocalDate getLocalDate(String str) {
        return str.equalsIgnoreCase("now") ?
                LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()):
                LocalDate.parse(str);
    }
}
