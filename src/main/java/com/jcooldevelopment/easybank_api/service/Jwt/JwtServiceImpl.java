package com.jcooldevelopment.easybank_api.service.Jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    public String getToken (UserDetails user) {
        return generateToken(new HashMap<>(), user); // HashMap for key-value pairs with claims (adicional info in token)
    }

    public String getUsernameFromToken(String token) {
        return this.getClaim(token, Claims::getSubject); // We inject method by reference
    }

    // It compares token payload's usercode with actual usercode in database
    public boolean isTokenValid (String token, UserDetails userDetails) {
        final String usercode = getUsernameFromToken(token);
        return (usercode.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails user) {
        var currentTime = System.currentTimeMillis();
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(user.getUsername())
            .issuedAt(new Date(currentTime))
            .expiration(new Date(currentTime + 1000*60*30)) // 30 minutes
            .signWith(getKey(), Jwts.SIG.HS256) // If second parameter is not given, the library decide the alrorithm to use
            .compact(); // Creates and serializes token
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Decodes Secret key
        return Keys.hmacShaKeyFor(keyBytes); // Creates new instance of Secret key
    }

    // https://github.com/jwtk/jjwt#jwt-claims
    private Claims getAllClaims (String token) { // 1:00:41
        return Jwts
            .parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    // Allows to get a specific claim from token.
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = this.getAllClaims(token);
        return claimsResolver.apply(claims); // It will execute the function with claims as first parameter
    }

    private Date getExpiration (String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Validates if token expiration is before than actual date
    private boolean isTokenExpired (String token) {
        return getExpiration(token).before(new Date());
    }
}
