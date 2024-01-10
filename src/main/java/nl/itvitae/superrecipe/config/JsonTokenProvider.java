package nl.itvitae.superrecipe.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nl.itvitae.superrecipe.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-in-ms}")
    private long jwtExpirationInMs;

    private JwtParser jwtParser;

    private final UserDetailsService service;

    @PostConstruct
    @SuppressWarnings({"deprecation", "RedundantCast", "RedundantSuppression"})
    private void init() {
        try {
            this.jwtParser = (JwtParser) Jwts.parser().setSigningKey(jwtSecret);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw e;
        }
    }

    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        JwtBuilder tokenBuilder = Jwts.builder();
        tokenBuilder.setClaims(claims);
        tokenBuilder.setSubject(userDetails.getUsername());
        tokenBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
        tokenBuilder.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs));
        tokenBuilder.signWith(SignatureAlgorithm.HS512, jwtSecret);
        return tokenBuilder.compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest request) {
        try {
            String token = resolveToken(request);
            if (token != null && !token.isEmpty()) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            request.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public UserDetails getUserDetailsFromToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String username = getUsernameFromToken(claims);
        return service.loadUserByUsername(username);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // "Bearer ".length() == 7
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUsernameFromToken(Claims claims) {
        return claims.getSubject();
    }

    public boolean validateClaims(Claims claims) {
        return claims.getExpiration().after(new Date());
    }
}
