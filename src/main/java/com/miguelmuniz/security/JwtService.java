package com.miguelmuniz.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;


import javax.crypto.SecretKey;
import java.util.Date;



@Service
    public class JwtService {

        @Value("${jwt.secret}")
        private String secret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

        public String generateToken(UserDetails user) {

            return Jwts.builder()
                    .subject(user.getUsername())
                    .issuedAt(new Date())
                    .expiration(
                            new Date(
                                    System.currentTimeMillis()
                                            + 1000 * 60 * 60
                            )
                    )
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        public String extractUsername(String token) {
            return extractAllClaims(token).getSubject();
        }

        public boolean isTokenValid(
                String token,
                UserDetails user
        ) {

            String username =
                    extractUsername(token);

            return username.equals(user.getUsername())
                    && !isTokenExpired(token);
        }

        private boolean isTokenExpired(String token) {

            return extractAllClaims(token)
                    .getExpiration()
                    .before(new Date());
        }

        private Claims extractAllClaims(String token) {

            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
    }

