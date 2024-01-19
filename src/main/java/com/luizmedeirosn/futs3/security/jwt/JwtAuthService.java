package com.luizmedeirosn.futs3.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.shared.dto.response.TokenDTO;
import com.luizmedeirosn.futs3.shared.exceptions.JwtAuthException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAuthService {

    @Value("${secret.key}")
    private String secretKey;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private String createToken(String username, Map<String, Object> claims, Date expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .signWith(getSignKey())
                .compact();
    }

    public TokenDTO generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        Date acessTokenExpirationTime = new Date(System.currentTimeMillis() + (1000L * 10L));
        Date refreshAcessTokenExpirationTime = new Date(System.currentTimeMillis() + (1000L * 30L));

        String accessToken = createToken(username, claims, acessTokenExpirationTime);
        String refreshToken = createToken(username, claims, refreshAcessTokenExpirationTime);

        return new TokenDTO(accessToken, refreshToken);
    }

    public TokenDTO refreshAccessToken(String refreshToken) {
        if (refreshToken.contains("Bearer ")) {
            refreshToken = refreshToken.substring("Bearer ".length());
            String username = extractUsername(refreshToken);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

            if (Boolean.TRUE.equals(validateToken(refreshToken, userDetails))) {
                Map<String, Object> claims = new HashMap<>();
                String accessToken = createToken(
                        username,
                        claims,
                        new Date(System.currentTimeMillis() + (1000L * 10L)));

                return new TokenDTO(accessToken, refreshToken);
            }
        }

        throw new JwtAuthException("Invalid refresh token");
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException | ExpiredJwtException e) {
            throw new JwtAuthException(e.getMessage());
        }

    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
