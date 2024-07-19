package com.luizmedeirosn.futs3.controllers;

import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import com.luizmedeirosn.futs3.shared.exceptions.PageableException;
import com.luizmedeirosn.futs3.shared.exceptions.StandardError;
import com.luizmedeirosn.futs3.utils.MessageFormatter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Objects;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<StandardError> noResourceFound(NoResourceFoundException e, HttpServletRequest request) {
    final var error = "No resource found";
    final var status = HttpStatus.NOT_FOUND;
    final var message = e.getMessage().replace(".", "");
    final var exception = new StandardError(status.value(), error, message, request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<StandardError> methodNotSupported(
      HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
    final var error = "Method not supported";
    final var status = HttpStatus.METHOD_NOT_ALLOWED;
    final var exception =
        new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<StandardError> endpointValueConversionError(
      MethodArgumentTypeMismatchException e, HttpServletRequest request) {
    final var error = "Error in endpoint value conversion";
    final var status = HttpStatus.BAD_REQUEST;
    final var exception =
        new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<StandardError> databaseError(DatabaseException e, HttpServletRequest request) {
    final var error = "Database error";
    final var status = HttpStatus.BAD_REQUEST;
    final var detail = MessageFormatter.databaseException(e.getMessage());
    final var exception = new StandardError(status.value(), error, detail, request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
    final var error = "Entity not found";
    final var status = HttpStatus.NOT_FOUND;
    final var exception =
        new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(PageableException.class)
  public ResponseEntity<StandardError> pageableError(PageableException e, HttpServletRequest request) {
    final var error = "Invalid pagination";
    final var status = HttpStatus.BAD_REQUEST;
    final var exception =
        new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(PropertyValueException.class)
  public ResponseEntity<StandardError> notNullError(PropertyValueException e, HttpServletRequest request) {
    final var error = "Database error";
    final var status = HttpStatus.BAD_REQUEST;
    final var detail = MessageFormatter.databaseException(e.getMessage());
    final var exception = new StandardError(status.value(), error, detail, request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<StandardError> dataIntegrityError(
      DataIntegrityViolationException e, HttpServletRequest request) {
    final var error = "Database error";
    final var status = HttpStatus.BAD_REQUEST;
    final var detail = MessageFormatter.databaseException(e.getMessage());
    final var exception = new StandardError(status.value(), error, detail, request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<StandardError> jsonParseError(HttpMessageNotReadableException e, HttpServletRequest request) {
    final var error = "Json parser error";
    final var status = HttpStatus.BAD_REQUEST;
    final var exception =
        new StandardError(status.value(), error, e.getMessage(), request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> methodArgumentNotValid(
      MethodArgumentNotValidException e, HttpServletRequest request) {
    final var error = Objects.requireNonNull(e.getBody().getDetail()).replace(".", "");
    final var status = HttpStatus.BAD_REQUEST;
    final var detail = MessageFormatter.methodArgumentNotValidException(e.getMessage());
    final var exception = new StandardError(status.value(), error, detail, request.getRequestURI(), Instant.now());
    return ResponseEntity.status(status).body(exception);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<StandardError> securityError(Exception e, HttpServletRequest request) {
    var error = new StandardError();
    error.setDetail(e.getMessage());
    error.setPath(request.getRequestURI());
    error.setTimestamp(Instant.now());

    var status = HttpStatus.FORBIDDEN;

    if (e instanceof BadCredentialsException) {
      status = HttpStatus.UNAUTHORIZED;
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

    } else if (e instanceof UsernameNotFoundException) {
      status = HttpStatus.NOT_FOUND;
      error.setError("User not found");

    } else if (e instanceof IllegalArgumentException) {
      status = HttpStatus.BAD_REQUEST;
      error.setError("Bad Request");

    } else if (e instanceof MaxUploadSizeExceededException) {
      status = HttpStatus.BAD_REQUEST;
      error.setError(e.getMessage());
      error.setDetail("The maximum allowed value for images is 1 MB");
    }

    error.setStatus(status.value());
    return ResponseEntity.status(status).body(error);
  }
}
