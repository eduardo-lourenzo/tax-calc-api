package br.edu.zup.tax_calc_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
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
    private RoleEnum roleEnum;

    public UserEntity(String username, String password, RoleEnum roleEnum) {
        this.username = username;
        this.password = password;
        this.roleEnum = roleEnum;
    }
}
