package com.example.personal_finance_tracker.Services.impl;

import com.example.personal_finance_tracker.Models.UserPrincipal;
import com.example.personal_finance_tracker.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JwtConfig config;

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails,
                config.getAccess().getSecret(),
                config.getAccess().getExpiration().toMillis());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails,
                config.getRefresh().getSecret(),
                config.getRefresh().getExpiration().toMillis());
    }

    private String buildToken(UserDetails userDetails, String secret, long ttlMillis) {
        UserPrincipal customUser = (UserPrincipal) userDetails;

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("userId", customUser.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
                .signWith(getKey(secret))
                .compact();
    }

    public String extractUsernameFromAccess(String token) {
        return extractClaim(token, Claims::getSubject, config.getAccess().getSecret());
    }

    public String extractUsernameFromRefresh(String token) {
        return extractClaim(token, Claims::getSubject, config.getRefresh().getSecret());
    }

    public Long extractUserIdFromAccess(String token) {
        Claims claims = extractAllClaims(token, config.getAccess().getSecret());
        Object idObject = claims.get("userId");

        if (idObject instanceof Integer) {
            return ((Integer) idObject).longValue();
        } else if (idObject instanceof Long) {
            return (Long) idObject;
        } else {
            return Long.parseLong(idObject.toString());
        }
    }

    public List<String> extractRolesFromAccess(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class), config.getAccess().getSecret());
    }

    public Collection<? extends GrantedAuthority> extractAuthoritiesFromAccess(String token) {
        List<String> roles = extractRolesFromAccess(token);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean validateAccessToken(String token, UserDetails userDetails) {
        final String username = extractUsernameFromAccess(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, config.getAccess().getSecret()));
    }

    private boolean isTokenExpired(String token, String secret) {
        return extractExpiration(token, secret).before(new Date());
    }

    private Date extractExpiration(String token, String secret) {
        return extractClaim(token, Claims::getExpiration, secret);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver, String secret) {
        final Claims claims = extractAllClaims(token, secret);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(getKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
