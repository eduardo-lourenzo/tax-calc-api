package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.RegisterRequestDTO;
import br.edu.zup.tax_calc_api.dtos.RegisterResponseDTO;

public interface UserService {
    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);
}
