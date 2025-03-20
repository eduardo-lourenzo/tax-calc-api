package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.RegisterRequestDTO;
import br.edu.zup.tax_calc_api.dtos.RegisterResponseDTO;
import br.edu.zup.tax_calc_api.exceptions.UserAlreadyExistsException;
import br.edu.zup.tax_calc_api.models.UserEntity;
import br.edu.zup.tax_calc_api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            throw new UserAlreadyExistsException("O usuário " + registerRequestDTO.getUsername() + " já existe.");
        }

        UserEntity newUser = new UserEntity(
                registerRequestDTO.getUsername(),
                passwordEncoder.encode(registerRequestDTO.getPassword()),
                registerRequestDTO.getRole()
        );

        UserEntity savedUser = userRepository.save(newUser);

        return new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }
}
