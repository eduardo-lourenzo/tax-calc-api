package br.edu.zup.tax_calc_api.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleValidationExceptions() {
        FieldError fieldError1 = new FieldError("objectName", "username", "O nome de usuário é obrigatório.");
        FieldError fieldError2 = new FieldError("objectName", "password", "A senha é obrigatória.");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).containsEntry("username", "O nome de usuário é obrigatório.");
        assertThat(response.getBody()).containsEntry("password", "A senha é obrigatória.");
    }

    @Test
    void shouldHandleConstraintViolationException() {
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);

        when(violation1.getMessage()).thenReturn("O nome de usuário é obrigatório.");
        when(violation2.getMessage()).thenReturn("A senha é obrigatória.");

        ConstraintViolationException exception = new ConstraintViolationException(Set.of(violation1, violation2));

        ResponseEntity<List<String>> response = globalExceptionHandler.handleConstraintViolationException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).containsExactlyInAnyOrder("O nome de usuário é obrigatório.", "A senha é obrigatória.");
    }

    @Test
    void shouldHandleUserAlreadyExistsException() {
        UserAlreadyExistsException exception = new UserAlreadyExistsException("O usuário já existe.");

        ResponseEntity<String> response = globalExceptionHandler.handleUserAlreadyExistsException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("O usuário já existe.");
    }

    @Test
    void shouldHandleTaxAlreadyExistsException() {
        TaxAlreadyExistsException exception = new TaxAlreadyExistsException("O imposto já existe.");

        ResponseEntity<String> response = globalExceptionHandler.handleTaxAlreadyExistsException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("O imposto já existe.");
    }

    @Test
    void shouldHandleTaxNotFoundException() {
        TaxNotFoundException exception = new TaxNotFoundException("O imposto não foi encontrado.");

        ResponseEntity<String> response = globalExceptionHandler.handleTaxNotFoundException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("O imposto não foi encontrado.");
    }

    @Test
    void shouldHandleUsernameNotFoundException() {
        UsernameNotFoundException exception = new UsernameNotFoundException("O nome de usuário testUser não foi encontrado.");

        ResponseEntity<String> response = globalExceptionHandler.handleUsernameNotFoundException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("O nome de usuário testUser não foi encontrado.");
    }
}