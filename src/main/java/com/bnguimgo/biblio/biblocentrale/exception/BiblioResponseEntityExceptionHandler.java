package com.bnguimgo.biblio.biblocentrale.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.TECHNICAL_ERROR;

// source: https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
@ControllerAdvice
@Slf4j
public class BiblioResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, //voir les annotations dans la classe package-info.java
            HttpStatusCode status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        BiblioError biblioError = new BiblioError(headers.getLocation().getPath(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, biblioError, headers, biblioError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        BiblioError biblioError = new BiblioError(headers.getLocation().getPath(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(
                biblioError, new HttpHeaders(), biblioError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        BiblioError biblioError = new BiblioError(headers.getLocation().getPath(), HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(biblioError, new HttpHeaders(), biblioError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));

        BiblioError biblioError = new BiblioError(headers.getLocation().getPath(), HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(
                biblioError, new HttpHeaders(), biblioError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        BiblioError biblioError = new BiblioError(headers.getLocation().getPath(), HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(
                biblioError, new HttpHeaders(), biblioError.getStatus());
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        BiblioError biblioError =
                new BiblioError(request.getRequestURL().toString(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                biblioError, new HttpHeaders(), biblioError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        BiblioError biblioError = new BiblioError(request.getRequestURL().toString(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(
                biblioError, new HttpHeaders(), biblioError.getStatus());
    }

    @ExceptionHandler({ BiblioException.class})
    public ResponseEntity<Object> handleBiblioExceptions(BiblioException ex, HttpServletRequest req, WebRequest request) {
        BiblioError biblioError = new BiblioError(req.getRequestURL().toString(), ex.getHttpStatus(), ex.getBiblioErrorEnum().getErrorCode(), ex.getBiblioErrorEnum().getError(), ex.getMessage());
        log.error(req.getRequestURL().toString(), ex);
        return new ResponseEntity<>(biblioError, biblioError.getStatus());
    }

    @ExceptionHandler({ BiblioRuntimeException.class })
    public ResponseEntity<Object> handleBiblioRuntimeExceptions(BiblioRuntimeException ex, HttpServletRequest req, WebRequest request) {
        BiblioError biblioError = new BiblioError(req.getRequestURL().toString(), ex.getHttpStatus(), ex.getBiblioErrorEnum().getErrorCode(), ex.getBiblioErrorEnum().getError(), ex.getMessage());
        log.error(req.getRequestURL().toString(), ex);
        return new ResponseEntity<>(biblioError, biblioError.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest req, WebRequest request) {
        BiblioError biblioError = new BiblioError(req.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR, TECHNICAL_ERROR.getErrorCode(), TECHNICAL_ERROR.getError(), ex.getMessage());
        log.error(req.getRequestURL().toString(), ex);
        return new ResponseEntity<>(biblioError, new HttpHeaders(), biblioError.getStatus());
    }

}