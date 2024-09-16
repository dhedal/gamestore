package com.ecf.gamestore.validation.constraint;

import com.ecf.gamestore.validation.UUIDValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UUIDValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UUIDConstraint {
    String message() default "Invalid UUID";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
