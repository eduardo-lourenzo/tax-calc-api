package br.edu.zup.tax_calc_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CalcResponseDTO {
    private String typeTax;
    private BigDecimal baseValue;
    private BigDecimal aliquot;
    private BigDecimal taxValue;
}
