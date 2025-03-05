package br.edu.zup.tax_calc_api.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations()
                .forEach(
                        constraintViolation ->
                                errors.add(
                                        constraintViolation.getMessage()
                                )
                );
        return ResponseEntity.badRequest().body(errors);
    }

}
