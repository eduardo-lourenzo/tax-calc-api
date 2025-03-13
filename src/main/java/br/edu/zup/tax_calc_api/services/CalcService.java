package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.CalcRequestDTO;

public interface CalcService {
    CalcRequestDTO calculateTax(CalcRequestDTO calcRequestDTO);
}
