package com.bnguimgo.biblio.biblocentrale.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class BiblioError {

    private final String url;
    private final String errorCode;
    private final HttpStatus status;
    private final List<String> errors;
    private final String details;

    public BiblioError(String url, HttpStatus status, String errorCode, String error, String details) {
        super();
        this.errorCode = errorCode;
        this.url = url;
        this.status = status;
        this.errors = Collections.singletonList(error);
        this.details = details;

    }

    public BiblioError(HttpStatus status, String details, List<String> errors) {
        super();
        this.status = status;
        this.details = details;
        this.errors = errors;
        this.url = "";
        this.errorCode = "";
    }

    public BiblioError(HttpStatus status, String details, String error) {
        super();
        this.status = status;
        this.details = details;
        this.errors = Collections.singletonList(error);
        this.url = "";
        this.errorCode = "";
    }

}