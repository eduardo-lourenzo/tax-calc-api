package br.edu.zup.tax_calc_api.models;

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
class TaxEntityTest {

    private final Validator validator;

    public TaxEntityTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidTaxEntity() {
        TaxEntity taxEntity = new TaxEntity();
        taxEntity.setName("Imposto de Renda");
        taxEntity.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxEntity.setAliquot(new BigDecimal("15.5"));

        Set<ConstraintViolation<TaxEntity>> violations = validator.validate(taxEntity);

        assertTrue(violations.isEmpty(), "A entidade deve ser válida.");
    }

    @Test
    void testInvalidName() {
        TaxEntity taxEntity = new TaxEntity();
        taxEntity.setName("");
        taxEntity.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxEntity.setAliquot(new BigDecimal("15.5"));

        Set<ConstraintViolation<TaxEntity>> violations = validator.validate(taxEntity);

        assertEquals(1, violations.size());
        assertEquals("O nome do imposto é obrigatório.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidDescription() {
        TaxEntity taxEntity = new TaxEntity();
        taxEntity.setName("Imposto de Renda");
        taxEntity.setDescription("");
        taxEntity.setAliquot(new BigDecimal("15.5"));

        Set<ConstraintViolation<TaxEntity>> violations = validator.validate(taxEntity);

        assertEquals(1, violations.size());
        assertEquals("A descrição imposto é obrigatória.", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidAliquot() {
        TaxEntity taxEntity = new TaxEntity();
        taxEntity.setName("Imposto de Renda");
        taxEntity.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxEntity.setAliquot(new BigDecimal("-5.0"));

        Set<ConstraintViolation<TaxEntity>> violations = validator.validate(taxEntity);

        assertEquals(1, violations.size());
        assertEquals("A alíquota deve ser maior que zero.", violations.iterator().next().getMessage());
    }

    @Test
    void testNullAliquot() {
        TaxEntity taxEntity = new TaxEntity();
        taxEntity.setName("Imposto de Renda");
        taxEntity.setDescription("Imposto sobre a renda de pessoas físicas e jurídicas.");
        taxEntity.setAliquot(null);

        Set<ConstraintViolation<TaxEntity>> violations = validator.validate(taxEntity);

        assertEquals(1, violations.size());
        assertEquals("A alíquota é obrigatória.", violations.iterator().next().getMessage());
    }
}
