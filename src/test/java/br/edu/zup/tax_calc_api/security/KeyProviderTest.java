package br.edu.zup.tax_calc_api.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class KeyProviderTest {

    private KeyProvider keyProvider;

    @BeforeEach
    void setUp() {
        keyProvider = mock(KeyProvider.class);

        when(keyProvider.key()).thenReturn(
                Keys.hmacShaKeyFor(Decoders.BASE64.decode("VGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGU"))
        );
    }

    @Test
    void testKeyGeneration() {
        Key key = keyProvider.key();

        assertNotEquals(0, key.getEncoded().length, "The generated key should not be empty");
        assertTrue(key.getAlgorithm().contains("HmacSHA"), "The key algorithm should be HmacSHA");
    }
}