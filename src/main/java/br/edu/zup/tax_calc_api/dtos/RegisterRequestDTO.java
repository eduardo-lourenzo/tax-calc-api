package br.edu.zup.tax_calc_api.dtos;

import br.edu.zup.tax_calc_api.models.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "O nome de usuário é obrigatório.")
    private String username;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;

    @NotNull(message = "O papel é obrigatório.")
    private RoleEnum role;
}