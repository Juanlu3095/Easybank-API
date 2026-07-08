package com.jcooldevelopment.easybank_api.service.Jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    public String getToken (UserDetails user) {
        return generateToken(new HashMap<>(), user); // HashMap for key-value pairs with claims (adicional info in token)
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails user) {
        var currentTime = System.currentTimeMillis();
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(user.getUsername())
            .issuedAt(new Date(currentTime))
            .expiration(new Date(currentTime + 1000*30)) // 30 minutes
            .signWith(getKey(), Jwts.SIG.HS256) // If second parameter is not given, the library decide the alrorithm to use
            .compact(); // Creates and serializes token
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decodes Secret key
        return Keys.hmacShaKeyFor(keyBytes); // Creates new instance of Secret key
    }

}
