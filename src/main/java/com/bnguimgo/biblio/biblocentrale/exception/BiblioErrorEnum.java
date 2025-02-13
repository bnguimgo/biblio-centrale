package com.bnguimgo.biblio.biblocentrale.exception;

import lombok.Getter;

@Getter
public enum BiblioErrorEnum {

    //Enum for Author errors
    AUTHOR_NOT_FOUND("AUTHOR_NOT_FOUND","Author not found"),

    //Enum for Author errors
    BOOK_NOT_FOUND("BOOK_NOT_FOUND","Book not found"),

    //Enum for Author errors
    STUDENT_NOT_FOUND("STUDENT_NOT_FOUND","Student not found"),

    //Enum for Author errors
    ITEM_NOT_FOUND("ITEM_NOT_FOUND","Item not found"),

    //Enum for Technical errors
    TECHNICAL_ERROR("TECHNICAL_ERROR","technical error occurred");

    private final String errorCode;

    private final String errors;
    BiblioErrorEnum(String errorCode, String errors) {
        this.errorCode = errorCode;
        this.errors = errors;
    }
}
