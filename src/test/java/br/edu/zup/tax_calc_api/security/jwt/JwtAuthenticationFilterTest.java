package br.edu.zup.tax_calc_api.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private UserDetailsService mockUserDetailsService;
    private JwtTokenProvider mockJwtTokenProvider;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private FilterChain mockFilterChain;

    @BeforeEach
    void setUp() {
        mockUserDetailsService = mock(UserDetailsService.class);
        mockJwtTokenProvider = mock(JwtTokenProvider.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(mockUserDetailsService, mockJwtTokenProvider);

        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockFilterChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilterInternal_WithValidToken_ShouldAuthenticateUser() throws ServletException, IOException {
        String token = "validToken";
        String username = "testUser";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(mockJwtTokenProvider.validateToken(token)).thenReturn(true);
        when(mockJwtTokenProvider.getUsername(token)).thenReturn(username);
        when(mockUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        assert authentication.getPrincipal().equals(userDetails);
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }

    @Test
    void testDoFilterInternal_WithInvalidToken_ShouldNotAuthenticateUser() throws ServletException, IOException {
        SecurityContextHolder.clearContext();

        String token = "invalidToken";
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(mockJwtTokenProvider.validateToken(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assert SecurityContextHolder.getContext().getAuthentication() ==
                null : "Authentication should be null for invalid token";
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
        verify(mockJwtTokenProvider, times(1)).validateToken(token);
        verify(mockRequest, times(1)).getHeader("Authorization");
    }

    @Test
    void testDoFilterInternal_WithNoToken_ShouldNotAuthenticateUser() throws ServletException, IOException {
        when(mockRequest.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assert SecurityContextHolder.getContext().getAuthentication() == null;
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }
}