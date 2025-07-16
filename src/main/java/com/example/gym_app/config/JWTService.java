package com.example.gym_app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret_key;

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    private SecretKey getSignInKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secret_key);
        return new SecretKeySpec(keyBytes,"HmacSHA256");
    }
    private Date extractExpiration(String token){
        return extraClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails.getUsername());
    }
    public String generateToken(Map<String, Object>extraClaims, String username){
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*10))
                .signWith(getSignInKey())
                .compact();
    }
    public String extractUsername(String token){
        return extraClaim(token,Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return  username.equals(userDetails.getUsername()) && !isTokenExpire(token);
    }
    public boolean isTokenExpire(String token){
        return extractExpiration(token).before(new Date());
    }

    public <T> T extraClaim(String token, Function<Claims, T>claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
}
