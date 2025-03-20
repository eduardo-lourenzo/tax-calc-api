package br.edu.zup.tax_calc_api.security.jwt;

import br.edu.zup.tax_calc_api.security.KeyProvider;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Long FIVE_HOURS_IN_MS = 18000000L;    // 5h * 60min = 300min * 60s = 18.000s * 1000ms = 18.000.000ms
    private final KeyProvider keyProvider;

    public JwtTokenProvider(KeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        Date currenteDate = new Date();
        Date expirationDate = new Date(currenteDate.getTime() + FIVE_HOURS_IN_MS);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currenteDate)
                .expiration(expirationDate)
                .claim("role", role)
                .signWith(keyProvider.key())
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) keyProvider.key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith((SecretKey) keyProvider.key())
                .build()
                .parse(token);

        return true;
    }
}