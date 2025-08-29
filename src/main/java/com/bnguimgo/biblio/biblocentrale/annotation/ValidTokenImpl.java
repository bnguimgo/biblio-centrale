package com.bnguimgo.biblio.biblocentrale.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class ValidTokenImpl implements ConstraintValidator<ValidToken, String> {

    @Override
    public boolean isValid(String idToken, ConstraintValidatorContext constraintValidatorContext) {

        return StringUtils.hasText(idToken);
    }
}
