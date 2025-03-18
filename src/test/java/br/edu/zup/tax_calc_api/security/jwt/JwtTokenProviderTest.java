package br.edu.zup.tax_calc_api.security.jwt;
import br.edu.zup.tax_calc_api.security.KeyProvider;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(new KeyProvider() {
            @Override
            public Key key() {
                String testKey = "VGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGU="; // Base64 de "Teste "x7
                return Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(testKey));
            }
        });
    }

    @Test
    void testGenerateToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "testUser",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String token = jwtTokenProvider.generateToken(authentication);


        assertNotNull(token, "Token should not be null");

        String[] tokenParts = token.split("\\.");
        assertEquals(3, tokenParts.length, "Token should have three parts separated by dots");

        for (String part : tokenParts) {
            assertFalse(part.isEmpty(), "Each part of the token should not be empty");
        }

        assertDoesNotThrow(() -> java.util.Base64.getDecoder().decode(tokenParts[0]), "Header should be a valid Base64 string");

        assertDoesNotThrow(() -> java.util.Base64.getDecoder().decode(tokenParts[1]), "Payload should be a valid Base64 string");

        assertFalse(tokenParts[2].isEmpty(), "Signature should not be empty");
    }

    @Test
    void testGetUsername() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "testUser",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        String token = jwtTokenProvider.generateToken(authentication);

        String username = jwtTokenProvider.getUsername(token);

        assertEquals("testUser", username, "Username should match the one used to generate the token");
    }

    @Test
    void testValidateToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "testUser",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        String token = jwtTokenProvider.generateToken(authentication);

        boolean isValid = jwtTokenProvider.validateToken(token);

        assertTrue(isValid, "Token should be valid");
    }
}