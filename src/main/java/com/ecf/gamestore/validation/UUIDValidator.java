package com.ecf.gamestore.validation;

import com.ecf.gamestore.validation.constraint.ValidUUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<ValidUUID, String> {
    @Override
    public void initialize(ValidUUID constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) return false;
        try {
            UUID.fromString(value);
            return true;
        } catch(IllegalArgumentException ex) {
            return false;
        }
    }
}
