package ink.th.auth.security.service;

import ink.th.auth.security.config.TokenProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProperties tokenProperties;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, tokenProperties.getValidity());
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long validity
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(this.getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (UnsupportedJwtException e) {
            log.error("JWT Token is unsupported (token: {}) : {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT token (token: {}) : {}", token, e.getMessage());
        } catch (SignatureException e) {
            log.error("Signature validation failed (token: {}) : {}", token, e.getMessage());
        } catch (SecurityException e) {
            log.error("Invalid JWT security exception (token: {}) : {}", token, e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Jwt token is expired (token: {}) : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT token is empty or null : {}", e.getMessage());
        } catch (Exception e) {
            log.error("JWT token is invalid (token: {}) : {}", token, e.getMessage());
        }
        return Jwts.claims().build();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
