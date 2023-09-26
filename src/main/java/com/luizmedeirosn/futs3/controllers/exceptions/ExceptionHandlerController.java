package com.luizmedeirosn.futs3.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.luizmedeirosn.futs3.services.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.services.exceptions.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerController {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(EntityNotFoundException e, HttpServletRequest request) {
        final String ERROR = "ENTITY NOT FOUND";
        final HttpStatus STATUS = HttpStatus.NOT_FOUND;
        final StandardError EXCEPTION = new StandardError(Instant.now(), STATUS.value(), ERROR, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        final String ERROR = "DATA BASE ERROR";
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST; 
        final StandardError EXCEPTION = new StandardError(Instant.now(), STATUS.value(), ERROR, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(STATUS).body(EXCEPTION);
    }

}
