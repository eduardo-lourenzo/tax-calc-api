package br.edu.zup.tax_calc_api.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import jakarta.servlet.ServletException;

import static org.mockito.Mockito.*;

class JwtAuthenticationEntryPointTest {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private AuthenticationException mockAuthException;

    @BeforeEach
    void setUp() {
        jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockAuthException = mock(AuthenticationException.class);
    }

    @Test
    void testCommence_ShouldSendUnauthorizedError() throws IOException, ServletException {
        String errorMessage = "Unauthorized access";
        when(mockAuthException.getMessage()).thenReturn(errorMessage);

        jwtAuthenticationEntryPoint.commence(mockRequest, mockResponse, mockAuthException);

        verify(mockResponse, times(1)).
                sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
    }
}