package com.ecf.gamestore.validation;

import com.ecf.gamestore.validation.constraint.ValidUUIDMap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.UUID;

public class UUIDMapValidator implements ConstraintValidator<ValidUUIDMap, Map<String, Integer>> {

    @Override
    public void initialize(ValidUUIDMap constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Map<String, Integer> articles, ConstraintValidatorContext context) {
        if (articles == null || articles.isEmpty()) {
            return false;
        }


        for (Map.Entry<String, Integer> entry : articles.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            try {
                UUID.fromString(key);
            } catch (IllegalArgumentException e) {
                return false;
            }

            if (value == null || value < 1) {
                return false;
            }
        }

        return true;
    }
}
