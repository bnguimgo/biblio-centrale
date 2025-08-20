package com.bnguimgo.biblio.biblocentrale.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@Constraint(validatedBy = {AuthorModifierDateValidatorImpl.class})
public @interface ValidDateComparator {

    //String message() default "{constraints.PasswordMatches.message}";
    String message() default "La date de modification doit être supérieure à la date de création";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}