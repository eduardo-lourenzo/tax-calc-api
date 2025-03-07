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

public class UserEntityTest {
    //Regarding the ConstraintViolationException
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        UserEntity userEntity = new UserEntity("validUsername", "validPassword", RoleEnum.USER);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid user.");
    }

    @Test
    void testValidAdmin() {
        UserEntity userEntity = new UserEntity("validUsername", "validPassword", RoleEnum.ADMIN);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid user.");
    }

    @Test
    void testUsernameIsBlank() {
        UserEntity userEntity = new UserEntity("", "validPassword", RoleEnum.USER);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertFalse(violations.isEmpty(), "Username should not be blank.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O nome de usuário é obrigatório.")));
    }

    @Test
    void testUsernameIsNull() {
        UserEntity userEntity = new UserEntity(null, "validPassword", RoleEnum.USER);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertFalse(violations.isEmpty(), "Username should not be null.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O nome de usuário é obrigatório.")));
    }

    @Test
    void testPasswordIsBlank() {
        UserEntity userEntity = new UserEntity("validUsername", "", RoleEnum.USER);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertFalse(violations.isEmpty(), "Password should not be blank.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("A senha é obrigatória.")));
    }

    @Test
    void testPasswordIsNull() {
        UserEntity userEntity = new UserEntity("validUsername", null, RoleEnum.USER);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertFalse(violations.isEmpty(), "Password should not be null.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("A senha é obrigatória.")));
    }

    @Test
    void testRoleIsNull() {
        UserEntity userEntity = new UserEntity("validUsername", "validPassword", null);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertFalse(violations.isEmpty(), "Role should not be null.");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("O papel é obrigatório.")));
    }
}
