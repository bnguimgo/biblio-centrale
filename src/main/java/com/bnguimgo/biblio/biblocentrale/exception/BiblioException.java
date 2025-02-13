package com.bnguimgo.biblio.biblocentrale.exception;

import lombok.Getter;

@Getter
//https://www.baeldung.com/rest-api-error-handling-best-practices
//Source: RuntimeException de JAVA
public class BiblioException extends Exception {

    private BiblioErrorEnum biblioErrorEnum;
    public BiblioException() {
        super();
    }

    public BiblioException(String message) {
        super(message);
    }

    public BiblioException(BiblioErrorEnum biblioErrorEnum, String details) {
        super(details);
        this.biblioErrorEnum = biblioErrorEnum;
    }

    public BiblioException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BiblioException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
