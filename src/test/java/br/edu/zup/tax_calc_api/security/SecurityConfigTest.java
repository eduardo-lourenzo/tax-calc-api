package br.edu.zup.tax_calc_api.security;

import br.edu.zup.tax_calc_api.security.jwt.JwtAuthenticationEntryPoint;
import br.edu.zup.tax_calc_api.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Mock
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.exceptionHandling(any())).thenReturn(httpSecurity);
        when(httpSecurity.addFilterBefore(any(), any())).thenReturn(httpSecurity);

        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(httpSecurity);
        assertThat(securityFilterChain).isNotNull();
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationManager mockAuthenticationManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mockAuthenticationManager);

        AuthenticationManager authenticationManager = securityConfig.authenticationManager(authenticationConfiguration);
        assertThat(authenticationManager).isNotNull();
    }
}