package nl.itvitae.superrecipe.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class JsonTokenFilter extends OncePerRequestFilter {

    private final JsonTokenProvider tokenProvider;

    public JsonTokenFilter(JsonTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = tokenProvider.resolveToken(request);
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Claims claims = tokenProvider.resolveClaims(request);
        if (claims != null && tokenProvider.validateClaims(claims)) {
            String username = claims.getSubject();
            UserDetails userDetails = tokenProvider.getUserDetailsFromToken(accessToken);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, "", authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}