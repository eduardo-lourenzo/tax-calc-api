package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.TaxRequestDTO;
import br.edu.zup.tax_calc_api.exceptions.TaxAlreadyExistsException;
import br.edu.zup.tax_calc_api.exceptions.TaxNotFoundException;
import br.edu.zup.tax_calc_api.models.TaxEntity;
import br.edu.zup.tax_calc_api.repositories.TaxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaxServiceImplTest {

    @Mock
    private TaxRepository taxRepository;

    @InjectMocks
    private TaxServiceImpl taxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTax() {
        TaxEntity tax1 = new TaxEntity();
        ReflectionTestUtils.setField(tax1, "id", 1L);
        tax1.setName("Imposto 1");
        tax1.setDescription("Descrição do imposto 1");
        tax1.setAliquot(new BigDecimal("10.123456"));

        TaxEntity tax2 = new TaxEntity();
        ReflectionTestUtils.setField(tax2, "id", 2L);
        tax2.setName("Imposto 2");
        tax2.setDescription("Descrição do imposto 2");
        tax2.setAliquot(new BigDecimal("20.654321"));

        when(taxRepository.findAll()).thenReturn(List.of(tax1, tax2));

        List<TaxEntity> taxes = taxService.getAllTax();

        assertEquals(2, taxes.size());
        verify(taxRepository, times(1)).findAll();
    }

    @Test
    void testCreateTax_Success() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Descrição do imposto");
        taxRequestDTO.setAliquot(new BigDecimal("15.123456"));

        when(taxRepository.existsByName(taxRequestDTO.getName())).thenReturn(false);

        TaxEntity savedTax = new TaxEntity();
        ReflectionTestUtils.setField(savedTax, "id", 1L);
        savedTax.setName(taxRequestDTO.getName());
        savedTax.setDescription(taxRequestDTO.getDescription());
        savedTax.setAliquot(taxRequestDTO.getAliquot());

        when(taxRepository.save(any(TaxEntity.class))).thenReturn(savedTax);

        TaxEntity result = taxService.createTax(taxRequestDTO);

        assertNotNull(result);
        assertEquals("Imposto de Renda", result.getName());
        assertEquals("Descrição do imposto", result.getDescription());
        assertEquals(new BigDecimal("15.123456"), result.getAliquot());

        verify(taxRepository, times(1)).existsByName(taxRequestDTO.getName());
        verify(taxRepository, times(1)).save(any(TaxEntity.class));
    }

    @Test
    void testCreateTax_AlreadyExists() {
        TaxRequestDTO taxRequestDTO = new TaxRequestDTO();
        taxRequestDTO.setName("Imposto de Renda");
        taxRequestDTO.setDescription("Descrição do imposto");
        taxRequestDTO.setAliquot(new BigDecimal("15.123456"));

        when(taxRepository.existsByName(taxRequestDTO.getName())).thenReturn(true);

        assertThrows(TaxAlreadyExistsException.class, () -> taxService.createTax(taxRequestDTO));

        verify(taxRepository, times(1)).existsByName(taxRequestDTO.getName());
        verify(taxRepository, never()).save(any(TaxEntity.class));
    }

    @Test
    void testGetTaxById_Success() {
        TaxEntity tax = new TaxEntity();
        ReflectionTestUtils.setField(tax, "id", 1L);
        tax.setName("Imposto de Renda");
        tax.setDescription("Descrição do imposto");
        tax.setAliquot(new BigDecimal("15.123456"));

        when(taxRepository.findById(1L)).thenReturn(Optional.of(tax));

        TaxEntity result = taxService.getTaxById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Imposto de Renda", result.getName());
        assertEquals("Descrição do imposto", result.getDescription());
        assertEquals(new BigDecimal("15.123456"), result.getAliquot());

        verify(taxRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaxById_NotFound() {
        when(taxRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaxNotFoundException.class, () -> taxService.getTaxById(1L));

        verify(taxRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTax_Success() {
        when(taxRepository.existsById(1L)).thenReturn(true);

        taxService.deleteTax(1L);

        verify(taxRepository, times(1)).existsById(1L);
        verify(taxRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTax_NotFound() {
        when(taxRepository.existsById(1L)).thenReturn(false);

        assertThrows(TaxNotFoundException.class, () -> taxService.deleteTax(1L));

        verify(taxRepository, times(1)).existsById(1L);
        verify(taxRepository, never()).deleteById(1L);
    }
}