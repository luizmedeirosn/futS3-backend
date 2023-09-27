package com.luizmedeirosn.futs3.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.luizmedeirosn.futs3.services.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.services.exceptions.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerController {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        final String ERROR = "ENTITY NOT FOUND";
        final HttpStatus STATUS = HttpStatus.NOT_FOUND;
        final StandardError EXCEPTION = new StandardError(Instant.now(), STATUS.value(), ERROR, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
     public ResponseEntity<StandardError> endpointValueConversionError(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        final String ERROR = "ERROR IN ENDPOINT VALUE CONVERSION";
        final HttpStatus STATUS = HttpStatus.NOT_FOUND;
        final StandardError EXCEPTION = new StandardError(Instant.now(), STATUS.value(), ERROR, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseError(DatabaseException e, HttpServletRequest request) {
        final String ERROR = "DATA BASE ERROR";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST; 
        final StandardError EXCEPTION = new StandardError(Instant.now(), STATUS.value(), ERROR, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> jsonParseError(HttpMessageNotReadableException e, HttpServletRequest request) {
        final String ERROR = "JSON PARSE ERROR";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST; 
        StandardError EXCEPTION = new StandardError(Instant.now(), STATUS.value(), ERROR, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

}
