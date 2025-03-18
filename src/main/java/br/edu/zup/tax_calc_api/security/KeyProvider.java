package br.edu.zup.tax_calc_api.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class KeyProvider {
    private  final String secretKey;

    public KeyProvider() {
        this.secretKey = System.getenv("JWT_SECRET_KEY");
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
