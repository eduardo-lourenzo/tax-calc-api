package br.edu.zup.tax_calc_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "taxes")
public class TaxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "O nome do imposto é obrigatório.")
    @Column(nullable = false, unique = true, length = 63)
    private String name;

    @NotBlank(message = "A descrição imposto é obrigatória.")
    @Column(nullable = false, length = 255)
    private String description;

    @NotNull(message = "A alíquota é obrigatória.")
    @Digits(integer = 6, fraction = 6, message = "Número inválido, máximo 6 inteiros e 6 decimais.")
    @Positive(message = "A alíquota deve ser maior que zero.")
    @Column(nullable = false, precision = 12, scale = 6)
    private BigDecimal aliquot;
}

