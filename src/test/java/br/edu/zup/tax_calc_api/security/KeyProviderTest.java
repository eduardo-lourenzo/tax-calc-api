package br.edu.zup.tax_calc_api.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class KeyProviderTest {

    private KeyProvider keyProvider;

    @BeforeEach
    void setUp() {
        keyProvider = new KeyProvider();

        ReflectionTestUtils.setField(
                keyProvider,
                "secretKey",
                "VGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGUgVGVzdGU"
        );
    }

    @Test
    void testKeyGeneration() {
        Key key = keyProvider.key();

        assertNotNull(key, "The generated key should not be null");
        assertNotEquals(0, key.getEncoded().length, "The generated key should not be empty");
        assertTrue(key.getAlgorithm().contains("HmacSHA"), "The key algorithm should be HmacSHA");
    }
}