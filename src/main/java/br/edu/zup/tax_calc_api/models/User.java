package br.edu.zup.tax_calc_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "A senha é obrigatória.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "O papel é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
