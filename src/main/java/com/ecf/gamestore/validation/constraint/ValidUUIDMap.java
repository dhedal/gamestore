package com.ecf.gamestore.validation.constraint;

import com.ecf.gamestore.validation.UUIDMapValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UUIDMapValidator.class)
public @interface ValidUUIDMap {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
