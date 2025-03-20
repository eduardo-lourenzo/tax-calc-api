package br.edu.zup.tax_calc_api.controllers;


import br.edu.zup.tax_calc_api.dtos.TaxRequestDTO;
import br.edu.zup.tax_calc_api.models.TaxEntity;
import br.edu.zup.tax_calc_api.services.TaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaxControllerTest {

    @Mock
    private TaxService taxService;

    @InjectMocks
    private TaxController taxController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTax() {
        TaxEntity tax1 = new TaxEntity();
        ReflectionTestUtils.setField(tax1, "id", 1L);
        tax1.setName("Tax1");
        tax1.setDescription("Description1");
        tax1.setAliquot(new BigDecimal("10.00"));

        TaxEntity tax2 = new TaxEntity();
        ReflectionTestUtils.setField(tax2, "id", 2L);
        tax2.setName("Tax2");
        tax2.setDescription("Description2");
        tax2.setAliquot(new BigDecimal("20.00"));

        List<TaxEntity> taxes = Arrays.asList(tax1, tax2);
        when(taxService.getAllTax()).thenReturn(taxes);

        ResponseEntity<List<TaxEntity>> response = taxController.getAllTax();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(taxService, times(1)).getAllTax();
    }

    @Test
    void testCreateTax() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Tax1");
        taxRequestDTO.setDescription("Description1");
        taxRequestDTO.setAliquot(new BigDecimal("10.00"));

        TaxEntity createdTax = new TaxEntity();
        ReflectionTestUtils.setField(createdTax, "id", 1L);
        createdTax.setName("Tax1");
        createdTax.setDescription("Description1");
        createdTax.setAliquot(new BigDecimal("10.00"));

        when(taxService.createTax(taxRequestDTO)).thenReturn(createdTax);

        ResponseEntity<TaxEntity> response = taxController.createTax(taxRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Tax1", response.getBody().getName());
        verify(taxService, times(1)).createTax(taxRequestDTO);
    }

    @Test
    void testGetTaxById() {
        Long taxId = 1L;
        TaxEntity tax = new TaxEntity();
        ReflectionTestUtils.setField(tax, "id", taxId);
        tax.setName("Tax1");
        tax.setDescription("Description1");
        tax.setAliquot(new BigDecimal("10.00"));

        when(taxService.getTaxById(taxId)).thenReturn(tax);

        ResponseEntity<TaxEntity> response = taxController.getTaxById(taxId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tax1", response.getBody().getName());
        verify(taxService, times(1)).getTaxById(taxId);
    }

    @Test
    void testDeleteTax() {
        Long taxId = 1L;
        doNothing().when(taxService).deleteTax(taxId);

        ResponseEntity<Void> response = taxController.deleteTax(taxId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taxService, times(1)).deleteTax(taxId);
    }
}