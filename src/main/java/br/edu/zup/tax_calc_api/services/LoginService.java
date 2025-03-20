package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.LoginRequestDTO;
import br.edu.zup.tax_calc_api.dtos.LoginResponseDTO;

public interface LoginService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
