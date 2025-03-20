package br.edu.zup.tax_calc_api.controllers;

import br.edu.zup.tax_calc_api.dtos.LoginRequestDTO;
import br.edu.zup.tax_calc_api.dtos.LoginResponseDTO;
import br.edu.zup.tax_calc_api.dtos.RegisterRequestDTO;
import br.edu.zup.tax_calc_api.dtos.RegisterResponseDTO;
import br.edu.zup.tax_calc_api.models.RoleEnum;
import br.edu.zup.tax_calc_api.services.LoginService;
import br.edu.zup.tax_calc_api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class UserControllerTest {

    private UserService userService;
    private LoginService loginService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        loginService = Mockito.mock(LoginService.class);
        userController = new UserController(userService, loginService);
    }

    @Test
    void testRegister() {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setUsername("testUser");
        requestDTO.setPassword("password123");
        requestDTO.setRole(RoleEnum.valueOf("USER"));

        RegisterResponseDTO responseDTO = new RegisterResponseDTO(1L, "testUser", RoleEnum.valueOf("USER"));

        Mockito.when(userService.register(any(RegisterRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<RegisterResponseDTO> response = userController.register(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testLogin() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("testUser");
        loginRequestDTO.setPassword("password123");

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO("mocked-jwt-token");

        Mockito.when(loginService.login(any(LoginRequestDTO.class))).thenReturn(loginResponseDTO);

        ResponseEntity<LoginResponseDTO> response = userController.login(loginRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponseDTO, response.getBody());
    }
}