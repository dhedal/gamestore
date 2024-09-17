package com.ecf.gamestore.validation.constraint;

import com.ecf.gamestore.validation.UUIDValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UUIDValidator.class)
public @interface ValidUUID {
    String message() default "Le format de l'UUID est invalide";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
