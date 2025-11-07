package fyi.dslab.car.maintenance.tracker.users.auth.util;

import fyi.dslab.car.maintenance.tracker.users.auth.config.JwtProperties;
import fyi.dslab.car.maintenance.tracker.users.auth.service.model.AuthenticatedUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    public String generateToken(AuthenticatedUserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.id());
        claims.put("email", user.email());
        claims.put("roles", user.getAuthorities());
        return createToken(claims, user.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String createToken(Map<String, Object> claims, String subject) {
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret()
                .getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMs()))
                .signWith(key)
                .compact();
    }

    private String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret()
                .getBytes(StandardCharsets.UTF_8));
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(key)
                .build();
        return jwtParser.parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
