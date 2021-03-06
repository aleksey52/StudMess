package com.spbstu.StudMess.security;

import com.spbstu.StudMess.exception.JwtAuthenticationException;
import com.spbstu.StudMess.model.enums.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String ROLES = "roles";

    private final UserDetailsService userDetailsService;

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long expirationTime;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(email)
                .addClaims(Map.of(ROLES, List.of(role.toString())))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expirationTime).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null,
                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(List.of()));
    }

    private String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String bearerToken) {
        if (Objects.isNull(bearerToken)) {
            throw new JwtAuthenticationException("Authorization token not found");
        }
        if (!bearerToken.startsWith(TOKEN_PREFIX)) {
            throw new JwtAuthenticationException(format("Token authorizations must be prefixed with {%s}", TOKEN_PREFIX));
        }
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(bearerToken.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException("Expired token", e);
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            throw new JwtAuthenticationException("Unsupported token", e);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is invalid", e);
        } catch (Exception e) {
            throw new JwtAuthenticationException("User authorization not resolved", e);
        }
    }
}
