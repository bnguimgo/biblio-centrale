package com.bnguimgo.biblio.biblocentrale.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
//https://www.baeldung.com/rest-api-error-handling-best-practices
//Source: RuntimeException de JAVA
public class BiblioRuntimeException extends RuntimeException {

    private final BiblioErrorEnum biblioErrorEnum;
    private final HttpStatus httpStatus;

    public BiblioRuntimeException(BiblioErrorEnum biblioErrorEnum, HttpStatus httpStatus, String details) {
        super(details);
        this.biblioErrorEnum = biblioErrorEnum;
        this.httpStatus = httpStatus;
    }

}
