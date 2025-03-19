package br.edu.zup.tax_calc_api.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxRequestDTO {
    @NotBlank(message = "O nome do imposto é obrigatório.")
    @JsonProperty("nome")
    private String name;

    @NotBlank(message = "A descrição imposto é obrigatória.")
    @JsonProperty("descricao")
    private String description;

    @NotNull(message = "A alíquota é obrigatória.")
    @Digits(integer = 6, fraction = 6, message = "Número inválido, máximo 6 inteiros e 6 decimais.")
    @Positive(message = "A alíquota deve ser maior que zero.")
    @JsonProperty("aliquota")
    private BigDecimal aliquot;
}
