package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.RegisterRequestDTO;
import br.edu.zup.tax_calc_api.dtos.RegisterResponseDTO;
import br.edu.zup.tax_calc_api.exceptions.UserAlreadyExistsException;
import br.edu.zup.tax_calc_api.models.RoleEnum;
import br.edu.zup.tax_calc_api.models.UserEntity;
import br.edu.zup.tax_calc_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceImplTest {

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    private RegisterRequestDTO registerRequestDTO;

    @BeforeEach
    void setUp() {
        registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setUsername("testUser");
        registerRequestDTO.setPassword("password123");
        registerRequestDTO.setRole(RoleEnum.USER);    }

    @Test
    void testRegister_Success() {
        when(userRepository.existsByUsername(registerRequestDTO.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerRequestDTO.getPassword())).thenReturn("encodedPassword");

        UserEntity savedUser = new UserEntity("testUser", "encodedPassword", RoleEnum.USER);
        ReflectionTestUtils.setField(savedUser, "id", 1L);

        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(savedUser);

        RegisterResponseDTO response = userService.register(registerRequestDTO);

        assertEquals(1L, response.getId());
        assertEquals("testUser", response.getUsername());
        assertEquals(RoleEnum.USER, response.getRole());

        verify(userRepository, times(1)).existsByUsername(registerRequestDTO.getUsername());
        verify(passwordEncoder, times(1)).encode(registerRequestDTO.getPassword());
        verify(userRepository, times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    void testRegister_UserAlreadyExists() {
        when(userRepository.existsByUsername(registerRequestDTO.getUsername())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.register(registerRequestDTO);
        });

        assertEquals("O usuário testUser já existe.", exception.getMessage());
        verify(userRepository, times(1)).existsByUsername(registerRequestDTO.getUsername());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}