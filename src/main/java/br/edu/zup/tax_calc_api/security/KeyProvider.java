package br.edu.zup.tax_calc_api.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class KeyProvider {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
