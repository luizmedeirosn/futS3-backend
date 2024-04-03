package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import com.luizmedeirosn.futs3.shared.exceptions.StandardError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> methodNotSupported(HttpRequestMethodNotSupportedException e,
            HttpServletRequest request) {
        final String ERROR = "Method not supported";
        final HttpStatus STATUS = HttpStatus.METHOD_NOT_ALLOWED;
        StandardError EXCEPTION = new StandardError(STATUS.value(), ERROR, e.getMessage(), request.getRequestURI(),
                Instant.now());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        final String ERROR = "Entity not found";
        final HttpStatus STATUS = HttpStatus.NOT_FOUND;
        StandardError EXCEPTION = new StandardError(STATUS.value(), ERROR, e.getMessage(), request.getRequestURI(),
                Instant.now());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> endpointValueConversionError(MethodArgumentTypeMismatchException e,
            HttpServletRequest request) {
        final String ERROR = "Error in endpoint value conversion";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        StandardError EXCEPTION = new StandardError(STATUS.value(), ERROR, e.getMessage(), request.getRequestURI(),
                Instant.now());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseError(DatabaseException e, HttpServletRequest request) {
        final String ERROR = "Database error";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        StandardError EXCEPTION = new StandardError(STATUS.value(), ERROR, e.getMessage(), request.getRequestURI(),
                Instant.now());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<StandardError> notNullError(PropertyValueException e, HttpServletRequest request) {
        final String ERROR = "Database error";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        StandardError EXCEPTION = new StandardError(STATUS.value(), ERROR, e.getMessage(), request.getRequestURI(),
                Instant.now());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityError(DataIntegrityViolationException e,
            HttpServletRequest request) {
        final String ERROR = "Database error";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        final String MESSAGE = "Update failure. Unique index, not null, check index violation";
        StandardError EXCEPTION = new StandardError(STATUS.value(), ERROR, MESSAGE, request.getRequestURI(),
                Instant.now());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> jsonParseError(HttpMessageNotReadableException e, HttpServletRequest request) {
        final String ERROR = "Json parser error";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        StandardError EXCEPTION = new StandardError(STATUS.value(), ERROR, e.getMessage(), request.getRequestURI(),
                Instant.now());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> argumentNotValidError(MethodArgumentNotValidException e, HttpServletRequest request) {
        final String ERROR = "Argument not valid";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        StandardError EXCEPTION = new StandardError(STATUS.value(), ERROR, e.getMessage(), request.getRequestURI(),
                Instant.now());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> securityError(Exception e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setDetail(e.getMessage());
        error.setPath(request.getRequestURI());
        error.setTimestamp(Instant.now());

        HttpStatus status = HttpStatus.FORBIDDEN;
        error.setStatus(status.value());

        if (e instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            error.setStatus(HttpStatus.UNAUTHORIZED.value());
            error.setError("Bad credentials");
            error.setDetail("Authentication failure");

        } else if (e instanceof AccessDeniedException) {
            error.setError("Access denied");
            error.setDetail("Not authorized");

        } else if (e instanceof ExpiredJwtException) {
            error.setError("JWT token expired");

        } else if (e instanceof SignatureException) {
            error.setError("JWT token signature invalid");

        } else if (e instanceof MalformedJwtException) {
            error.setError("Malformed JWT token");

        } else if (e instanceof DecodingException) {
            error.setError("Decoding failure");

        } else if(e instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            error.setError("Bad Request");
        }

        return ResponseEntity.status(status).body(error);
    }

}