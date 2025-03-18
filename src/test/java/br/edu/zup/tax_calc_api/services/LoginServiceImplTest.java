package br.edu.zup.tax_calc_api.services;
import br.edu.zup.tax_calc_api.dtos.LoginRequestDTO;
import br.edu.zup.tax_calc_api.dtos.LoginResponseDTO;
import br.edu.zup.tax_calc_api.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private LoginServiceImpl loginService;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        loginService = new LoginServiceImpl(authenticationManager, jwtTokenProvider);
    }

    @Test
    void shouldAuthenticateAndGenerateToken() {
        String username = "testUser";
        String password = "testPassword";
        String token = "mockedToken";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername(username);
        loginRequestDTO.setPassword(password);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(token);

        LoginResponseDTO response = loginService.login(loginRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo(token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(1)).generateToken(authentication);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(authentication);
    }

    @Test
    void shouldThrowAuthenticationExceptionWhenAuthenticationFails() {
        String username = "testUser";
        String password = "wrongPassword";
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername(username);
        loginRequestDTO.setPassword(password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication failed") {});

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            loginService.login(loginRequestDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("Authentication failed");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtTokenProvider);
    }
}
