package br.edu.zup.tax_calc_api.dtos;

import br.edu.zup.tax_calc_api.models.RoleEnum;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private RoleEnum role;
}