package br.edu.zup.tax_calc_api.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalcRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCalcRequestDTO() {
        CalcRequestDTO dto = new CalcRequestDTO();
        dto.setTypeTaxId(1L);
        dto.setBaseValue(new BigDecimal("100.00"));

        Set<ConstraintViolation<CalcRequestDTO>> violations = validator.validate(dto);

        assertEquals(0, violations.size(), "There should be no validation errors for a valid DTO");
    }

    @Test
    void testTypeTaxIdIsNull() {
        CalcRequestDTO dto = new CalcRequestDTO();
        dto.setTypeTaxId(null);
        dto.setBaseValue(new BigDecimal("100.00"));

        Set<ConstraintViolation<CalcRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size(), "There should be one validation error for null typeTaxId");
        assertEquals("O identificador do tipo do imposto é obrigatório.",
                violations.iterator().next().getMessage());
    }

    @Test
    void testBaseValueIsNull() {
        CalcRequestDTO dto = new CalcRequestDTO();
        dto.setTypeTaxId(1L);
        dto.setBaseValue(null);

        Set<ConstraintViolation<CalcRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size(), "There should be one validation error for null baseValue");
        assertEquals("O valor base é obrigatório.", violations.iterator().next().getMessage());
    }

    @Test
    void testBaseValueIsNegative() {
        CalcRequestDTO dto = new CalcRequestDTO();
        dto.setTypeTaxId(1L);
        dto.setBaseValue(new BigDecimal("-100.00"));

        Set<ConstraintViolation<CalcRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size(), "There should be one validation error for negative baseValue");
        assertEquals("O valor base deve ser maior que zero.", violations.iterator().next().getMessage());
    }

    @Test
    void testBaseValueExceedsMaxIntegers() {
        CalcRequestDTO dto = new CalcRequestDTO();
        dto.setTypeTaxId(1L);
        dto.setBaseValue(new BigDecimal("1000000000000.00"));

        Set<ConstraintViolation<CalcRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size(), "There should be one validation error for exceeding max integers");
        assertEquals("Valor base inválido, máximo 12 inteiros e 2 decimais.",
                violations.iterator().next().getMessage());
    }

    @Test
    void testBaseValueExceedsMaxDecimals() {
        CalcRequestDTO dto = new CalcRequestDTO();
        dto.setTypeTaxId(1L);
        dto.setBaseValue(new BigDecimal("1000.123"));

        Set<ConstraintViolation<CalcRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size(), "There should be one validation error for exceeding max decimals");
        assertEquals("Valor base inválido, máximo 12 inteiros e 2 decimais.",
                violations.iterator().next().getMessage());
    }
}