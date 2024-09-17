package com.ecf.gamestore.validation;

import com.ecf.gamestore.validation.constraint.ValidPickupDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class OrderPickupDateValidator implements ConstraintValidator<ValidPickupDate, LocalDate> {
    private int maxDays;

    @Override
    public void initialize(ValidPickupDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.maxDays = constraintAnnotation.max();
        this.maxDays = this.maxDays < 0 ? 0 : this.maxDays;
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if(value == null) return false;

        LocalDate now = LocalDate.now();

        LocalDate maxDate = now.plusDays(this.maxDays);
        boolean isWithInRange = !value.isBefore(now) && !value.isAfter(maxDate);

        DayOfWeek dayOfWeek = value.getDayOfWeek();
        boolean isInvaliday = dayOfWeek == DayOfWeek.SUNDAY ||
                dayOfWeek == DayOfWeek.MONDAY;

        return isWithInRange && ! isInvaliday;
    }
}
