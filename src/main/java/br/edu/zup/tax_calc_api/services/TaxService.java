package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.TaxRequestDTO;
import br.edu.zup.tax_calc_api.models.TaxEntity;

import java.util.List;

public interface TaxService {
    List<TaxEntity> getAllTax();
    TaxEntity createTax(TaxRequestDTO taxRequestDTO);
    TaxEntity getTaxById(Long id);
    void deleteTax(Long id);
}
