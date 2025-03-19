package br.edu.zup.tax_calc_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalcRequestDTO {

    @NotNull(message = "O identificador do tipo do imposto é obrigatório.")
    @JsonProperty("tipoImpostoId")
    private Long typeTaxId;

    //12 = 1 trillion - 1
    @NotNull(message = "O valor base é obrigatório.")
    @Digits(integer = 12, fraction = 2, message = "Valor base inválido, máximo 12 inteiros e 2 decimais.")
    @Positive(message = "O valor base deve ser maior que zero.")
    @JsonProperty("valorBase")
    private BigDecimal baseValue;
}
