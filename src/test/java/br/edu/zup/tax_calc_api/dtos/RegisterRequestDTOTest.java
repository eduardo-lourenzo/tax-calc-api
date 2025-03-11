package br.edu.zup.tax_calc_api.dtos;

import br.edu.zup.tax_calc_api.models.RoleEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RegisterRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRegisterRequestDTO() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setUsername("validUser");
        dto.setPassword("validPassword");
        dto.setRole(RoleEnum.USER);

        Set<ConstraintViolation<RegisterRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "There should be no validation errors");
    }

    @Test
    void testInvalidRegisterRequestDTO_UsernameBlank() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setUsername("");
        dto.setPassword("validPassword");
        dto.setRole(RoleEnum.USER);

        Set<ConstraintViolation<RegisterRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("O nome de usuário é obrigatório.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidRegisterRequestDTO_PasswordBlank() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setUsername("validUser");
        dto.setPassword("");
        dto.setRole(RoleEnum.USER);

        Set<ConstraintViolation<RegisterRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("A senha é obrigatória.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidRegisterRequestDTO_RoleNull() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setUsername("validUser");
        dto.setPassword("validPassword");
        dto.setRole(null);

        Set<ConstraintViolation<RegisterRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("O papel é obrigatório.", violations.iterator().next().getMessage());
    }
}