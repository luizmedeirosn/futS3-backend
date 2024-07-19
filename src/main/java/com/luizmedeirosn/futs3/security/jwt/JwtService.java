package com.luizmedeirosn.futs3.security.jwt;

import com.luizmedeirosn.futs3.security.user.UserDetailsServiceImpl;
import com.luizmedeirosn.futs3.shared.dto.response.TokenResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final UserDetailsServiceImpl userDetailsServiceImpl;

  @Value("${security.jwt.token.secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-lenght}")
  private Long expireLenght;

  public JwtService(UserDetailsServiceImpl userDetailsServiceImpl) {
    this.userDetailsServiceImpl = userDetailsServiceImpl;
  }

  private String createToken(String username, Map<String, Object> claims, Date expirationTime) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expirationTime)
        .signWith(getSignKey())
        .compact();
  }

  public TokenResponseDTO generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    Date acessTokenExpirationTime = new Date(System.currentTimeMillis() + (expireLenght));
    Date refreshTokenExpirationTime = new Date(System.currentTimeMillis() + (expireLenght * 24L));

    String accessToken = createToken(username, claims, acessTokenExpirationTime);
    String refreshToken = createToken(username, claims, refreshTokenExpirationTime);

    return new TokenResponseDTO(accessToken, refreshToken);
  }

  public TokenResponseDTO refreshToken(String refreshToken) {
    if (refreshToken.contains("Bearer ")) {
      refreshToken = refreshToken.substring("Bearer ".length());
      String username = extractUsername(refreshToken);
      UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

      if (Boolean.TRUE.equals(validateToken(refreshToken, userDetails))) {
        Map<String, Object> claims = new HashMap<>();
        String accessToken = createToken(username, claims, new Date(System.currentTimeMillis() + expireLenght));

        return new TokenResponseDTO(accessToken, refreshToken);
      }
    }

    throw new MalformedJwtException("Invalid JWT Signature");
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
    return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
