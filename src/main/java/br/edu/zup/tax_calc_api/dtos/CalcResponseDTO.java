package br.edu.zup.tax_calc_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CalcResponseDTO {

    @JsonProperty("tipoImposto")
    private String typeTax;

    @JsonProperty("valorBase")
    private BigDecimal baseValue;

    @JsonProperty("aliquota")
    private BigDecimal aliquot;

    @JsonProperty("valorImposto")
    private BigDecimal taxValue;
}
