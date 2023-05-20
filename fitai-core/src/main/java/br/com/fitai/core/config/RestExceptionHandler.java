package br.com.fitai.core.config;

import br.com.fitai.core.exceptions.RestError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice // departamento de crises
public class RestExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestError> constraintViolationExceptionHandler(){
        return ResponseEntity.badRequest().body(new RestError(400, "campos inválidos"));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<RestError> responseStatusExceptionHandler(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode()).body(
                new RestError(e.getStatusCode().value(), e.getLocalizedMessage())
        );
    }
}
