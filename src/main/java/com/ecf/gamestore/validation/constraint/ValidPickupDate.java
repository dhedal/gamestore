package com.ecf.gamestore.validation.constraint;


import com.ecf.gamestore.validation.OrderPickupDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderPickupDateValidator.class)
public @interface ValidPickupDate {
    String message() default "La date doit être comprise entre aujourd'hui et {max} jours, et ne doit pas tomber un lundi ou un dimanche";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int max() default 7;
}
