package br.edu.zup.tax_calc_api.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    //Regarding the ConstraintViolationException
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        User user = new User("validUsername", "validPassword", Role.ROLE_USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid user.");
    }

    @Test
    void testValidAdmin() {
        User user = new User("validUsername", "validPassword", Role.ROLE_ADMIN);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid user.");
    }

    @Test
    void testUsernameIsBlank() {
        User user = new User("", "validPassword", Role.ROLE_USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Username should not be blank.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O nome de usuário é obrigatório.")));
    }

    @Test
    void testUsernameIsNull() {
        User user = new User(null, "validPassword", Role.ROLE_USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Username should not be null.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O nome de usuário é obrigatório.")));
    }

    @Test
    void testPasswordIsBlank() {
        User user = new User("validUsername", "", Role.ROLE_USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Password should not be blank.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("A senha é obrigatória.")));
    }

    @Test
    void testPasswordIsNull() {
        User user = new User("validUsername", null, Role.ROLE_USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Password should not be null.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("A senha é obrigatória.")));
    }

    @Test
    void testRoleIsNull() {
        User user = new User("validUsername", "validPassword", null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Role should not be null.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O papel é obrigatório.")));
    }
}
