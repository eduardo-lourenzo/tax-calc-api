package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.CalcRequestDTO;
import br.edu.zup.tax_calc_api.dtos.CalcResponseDTO;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalcServiceImplTest {

    @Mock
    private TaxRepository taxRepository;

    @InjectMocks
    private CalcServiceImpl calcService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateTax_Success() {
        Long typeTaxId = 1L;
        BigDecimal baseValue = new BigDecimal("1000.00");
        BigDecimal aliquot = new BigDecimal("10.00");

        TaxEntity taxEntity = new TaxEntity();
        ReflectionTestUtils.setField(taxEntity, "id", typeTaxId); // Usando ReflectionTestUtils
        taxEntity.setName("Imposto Teste");
        taxEntity.setAliquot(aliquot);

        CalcRequestDTO calcRequestDTO = new CalcRequestDTO();
        calcRequestDTO.setTypeTaxId(typeTaxId);
        calcRequestDTO.setBaseValue(baseValue);

        when(taxRepository.findById(typeTaxId)).thenReturn(Optional.of(taxEntity));

        CalcResponseDTO response = calcService.calculateTax(calcRequestDTO);

        assertNotNull(response);
        assertEquals("Imposto Teste", response.getTypeTax());
        assertEquals(baseValue, response.getBaseValue());
        assertEquals(aliquot, response.getAliquot());
        assertEquals(new BigDecimal("100.00"), response.getTaxValue());
        verify(taxRepository, times(1)).findById(typeTaxId);
    }

    @Test
    void calculateTax_TaxNotFound() {
        Long typeTaxId = 1L;

        CalcRequestDTO calcRequestDTO = new CalcRequestDTO();
        calcRequestDTO.setTypeTaxId(typeTaxId);
        calcRequestDTO.setBaseValue(new BigDecimal("1000.00"));

        when(taxRepository.findById(typeTaxId)).thenReturn(Optional.empty());

        TaxNotFoundException exception = assertThrows(TaxNotFoundException.class, () -> {
            calcService.calculateTax(calcRequestDTO);
        });

        assertEquals("O imposto com id 1 não foi encontrado.", exception.getMessage());
        verify(taxRepository, times(1)).findById(typeTaxId);
    }

    @Test
    void calculateTax_RoundingUpToTwoDecimalPlaces() {
        Long typeTaxId = 1L;
        BigDecimal baseValue = new BigDecimal("1000.00");
        BigDecimal aliquot = new BigDecimal("50.0005");
        TaxEntity taxEntity = new TaxEntity();
        ReflectionTestUtils.setField(taxEntity, "id", typeTaxId);
        taxEntity.setName("Imposto Teste");
        taxEntity.setAliquot(aliquot);
        CalcRequestDTO calcRequestDTO = new CalcRequestDTO();
        calcRequestDTO.setTypeTaxId(typeTaxId);
        calcRequestDTO.setBaseValue(baseValue);
        when(taxRepository.findById(typeTaxId)).thenReturn(Optional.of(taxEntity));

        CalcResponseDTO response = calcService.calculateTax(calcRequestDTO);

        assertNotNull(response);
        assertEquals("Imposto Teste", response.getTypeTax());
        assertEquals(baseValue, response.getBaseValue());
        assertEquals(aliquot, response.getAliquot());
        assertEquals(new BigDecimal("500.01"), response.getTaxValue());
        verify(taxRepository, times(1)).findById(typeTaxId);
    }
}