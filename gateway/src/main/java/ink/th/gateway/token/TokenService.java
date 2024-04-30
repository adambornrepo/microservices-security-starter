package ink.th.gateway.token;

import ink.th.gateway.utils.TokenProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProperties tokenProperties;

    public boolean isTokenValid(String token) {
        Date expirationDate = extractExpiration(token);
        return !(expirationDate == null || expirationDate.before(new Date()));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
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