package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.CalcRequestDTO;
import br.edu.zup.tax_calc_api.dtos.CalcResponseDTO;

public interface CalcService {
    CalcResponseDTO calculateTax(CalcRequestDTO calcRequestDTO);
}
