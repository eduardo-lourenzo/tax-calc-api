package br.edu.zup.tax_calc_api.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TaxRequestDTOTest {

    private final Validator validator;

    public TaxRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidTaxRequestDTO() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxRequestDTO.setAliquot(new BigDecimal("15.5"));

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertTrue(violations.isEmpty(), "A DTO deve ser válida.");
    }

    @Test
    void testInvalidName() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("");
        taxRequestDTO.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxRequestDTO.setAliquot(new BigDecimal("15.5"));

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertEquals(1, violations.size());
        assertEquals("O nome do imposto é obrigatório.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidDescription() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("");
        taxRequestDTO.setAliquot(new BigDecimal("15.5"));

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertEquals(1, violations.size());
        assertEquals("A descrição imposto é obrigatória.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidAliquotNegative() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxRequestDTO.setAliquot(new BigDecimal("-5.25"));

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertEquals(1, violations.size());
        assertEquals("A alíquota deve ser maior que zero.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidAliquotNull() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxRequestDTO.setAliquot(null);

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertEquals(1, violations.size());
        assertEquals("A alíquota é obrigatória.", violations.iterator().next().getMessage());
    }

    @Test
    void testValidAliquotMaxInteger() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxRequestDTO.setAliquot(new BigDecimal("123456.0"));

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertTrue(violations.isEmpty(), "A alíquota com o máximo de inteiros deve ser válida.");
    }

    @Test
    void testValidAliquotMaxDecimal() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxRequestDTO.setAliquot(new BigDecimal("0.123456"));

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertTrue(violations.isEmpty(), "A alíquota com o máximo de decimais deve ser válida.");
    }

    @Test
    void testInvalidAliquotExceedsMaxInteger() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxRequestDTO.setAliquot(new BigDecimal("1234567.123456"));

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertEquals(1, violations.size());
        assertEquals("Número inválido, máximo 6 inteiros e 6 decimais.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidAliquotExceedsMaxDecimal() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxRequestDTO.setAliquot(new BigDecimal("123456.1234567"));

        Set<ConstraintViolation<TaxRequestDTO>> violations = validator.validate(taxRequestDTO);
        assertEquals(1, violations.size());
        assertEquals("Número inválido, máximo 6 inteiros e 6 decimais.", violations.iterator().next().getMessage());
    }
}