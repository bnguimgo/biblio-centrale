package com.bnguimgo.biblio.biblocentrale.exception;

import lombok.Getter;

@Getter
public enum BiblioErrorEnum {

    //Enum for Author errors
    AUTHOR_NOT_FOUND("AUTHOR_NOT_FOUND","Author not found"),
    AUTHOR_CANNOT_DELETE("CANNOT_DELETE_AUTHOR","Cannot delete Author with remaining books"),

    //Enum for Author errors
    BOOK_NOT_FOUND("BOOK_NOT_FOUND","Book not found"),
    BOOK_CANNOT_DELETE("CANNOT_DELETE_BOOK","Cannot delete borrowed Book"),
    BOOK_DUPLICATED_BORROW("CANNOT_DUPLICATE_BORROW_BOOK","Cannot duplicate borrow Book for the same student"),

    //Enum for Author errors
    STUDENT_NOT_FOUND("STUDENT_NOT_FOUND","Student not found"),
    STUDENT_CANNOT_DELETE("CANNOT_DELETE_STUDENT","Cannot delete Student with remaining books"),

    //Enum for Technical errors
    TECHNICAL_ERROR("TECHNICAL_ERROR","technical error occurred"),

    //Token validation
    TOKEN_NULL("TOKEN_NULL","token cannot be null"),
    TOKEN_INVALID("TOKEN_INVALID","invalid token");

    private final String errorCode;

    private final String error;
    BiblioErrorEnum(String errorCode, String error) {
        this.errorCode = errorCode;
        this.error = error;
    }
}
