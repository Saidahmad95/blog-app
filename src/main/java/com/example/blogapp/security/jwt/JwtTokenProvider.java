package com.example.blogapp.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.blogapp.security.user.JwtUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.exp.date}")
    private Long expirationMills;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMills))
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        String username = Jwts
                .parserBuilder().setSigningKey(getSingInKey())
                .build().parseClaimsJws(token)
                .getBody().getSubject();
        return username;
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder().setSigningKey(getSingInKey())
                    .build().parseClaimsJws(token);

            return !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts
                .parserBuilder().setSigningKey(getSingInKey())
                .build().parseClaimsJws(token).getBody().getExpiration();
                
        return new Date().after(expiration);
    }

    private Key getSingInKey() {
        byte[] decodedSecret = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodedSecret);
    }

}
