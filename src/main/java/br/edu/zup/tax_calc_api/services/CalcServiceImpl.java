package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.CalcRequestDTO;
import br.edu.zup.tax_calc_api.dtos.CalcResponseDTO;
import br.edu.zup.tax_calc_api.exceptions.TaxNotFoundException;
import br.edu.zup.tax_calc_api.models.TaxEntity;
import br.edu.zup.tax_calc_api.repositories.TaxRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalcServiceImpl implements CalcService {
    private final TaxRepository taxRepository;

    public CalcServiceImpl(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Override
    public CalcResponseDTO calculateTax(CalcRequestDTO calcRequestDTO) {

        TaxEntity typeTax = taxRepository.findById(calcRequestDTO.getTypeTaxId()).orElseThrow(() ->
                new TaxNotFoundException("O imposto com id " + calcRequestDTO.getTypeTaxId() + " não foi encontrado.")
        );

        BigDecimal taxValue = calcRequestDTO.getBaseValue()
                .multiply(typeTax.getAliquot())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return new CalcResponseDTO(
                typeTax.getName(),
                calcRequestDTO.getBaseValue(),
                typeTax.getAliquot(),
                taxValue
        );
    }
}