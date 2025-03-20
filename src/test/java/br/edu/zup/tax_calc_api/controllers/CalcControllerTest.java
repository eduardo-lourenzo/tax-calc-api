package br.edu.zup.tax_calc_api.controllers;

import br.edu.zup.tax_calc_api.dtos.CalcRequestDTO;
import br.edu.zup.tax_calc_api.dtos.CalcResponseDTO;
import br.edu.zup.tax_calc_api.services.CalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalcControllerTest {

    private CalcService calcService;
    private CalcController calcController;

    @BeforeEach
    void setUp() {
        calcService = mock(CalcService.class);

        calcController = new CalcController(calcService);
    }

    @Test
    void testCalculateTax() {
        CalcRequestDTO requestDTO = new CalcRequestDTO();
        requestDTO.setTypeTaxId(1L);
        requestDTO.setBaseValue(new BigDecimal("1000.00"));

        CalcResponseDTO responseDTO = new CalcResponseDTO(
                "Imposto Teste",
                new BigDecimal("1000.00"),
                new BigDecimal("10.00"),
                new BigDecimal("100.00")
        );

        when(calcService.calculateTax(Mockito.any(CalcRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<CalcResponseDTO> response = calcController.calculateTax(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }
}
