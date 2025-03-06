package br.edu.zup.tax_calc_api.dtos;

import br.edu.zup.tax_calc_api.models.RoleEnum;
import lombok.Data;

@Data
public class RegisterResponseDTO {
    private Long id;
    String username;
    RoleEnum role;
}
