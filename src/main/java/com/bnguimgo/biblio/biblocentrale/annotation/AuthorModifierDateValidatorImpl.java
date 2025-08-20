package com.bnguimgo.biblio.biblocentrale.annotation;

import com.bnguimgo.biblio.biblocentrale.dto.AuthorDTO;
import com.bnguimgo.biblio.biblocentrale.entity.Author;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthorModifierDateValidatorImpl implements ConstraintValidator<ValidDateComparator, Object> {


    @Override
    public boolean isValid(Object authorDtoObj, ConstraintValidatorContext constraintValidatorContext) {
        if(authorDtoObj instanceof AuthorDTO authorDTO ) {
            if (null == authorDTO.getCreatedDate()) {
                return false;
            }

            if(null == authorDTO.getModifiedDate()) {
                return true;
            }
            return authorDTO.getCreatedDate().isBefore(authorDTO.getModifiedDate());
        } else if(authorDtoObj instanceof Author author ) {
            if (null == author.getCreatedDate()) {
                return false;
            }

            if(null == author.getModifiedDate()) {
                return true;
            }
            return author.getCreatedDate().isBefore(author.getModifiedDate());
        }
        return false;
    }
}
