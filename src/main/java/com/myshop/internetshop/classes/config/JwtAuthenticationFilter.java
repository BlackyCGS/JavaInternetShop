package com.myshop.internetshop.classes.config;

import com.myshop.internetshop.classes.services.CustomUserDetailsService;
import com.myshop.internetshop.classes.services.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    @Value("${security.jwt.refresh.expiration-time}")
    private long refreshExpiration;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = getJwtFromCookies(request);
            if (jwt == null) {
                String jwtr = getJwtRefreshFromCookies(request);
                if (jwtr == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
                List<String> jwtTokens = jwtService.updateTokenPair(jwtr);
                jwt = jwtTokens.get(0);
                Cookie jwtAccess = new Cookie("jwt", jwt);
                jwtAccess.setHttpOnly(true);
                jwtAccess.setSecure(true);
                jwtAccess.setPath("/");
                jwtAccess.setMaxAge((int) jwtExpiration);
                jwtAccess.setDomain("localhost");

                jwtr = jwtTokens.get(1);
                Cookie jwtRefresh = new Cookie("jwtr", jwtr);
                jwtRefresh.setHttpOnly(true);
                jwtRefresh.setSecure(true);
                jwtRefresh.setPath("/");
                jwtRefresh.setMaxAge((int) refreshExpiration);
                jwtRefresh.setDomain("localhost");

                response.addCookie(jwtAccess);
                response.addCookie(jwtRefresh);

            }
            if (jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }
            String username = jwtService.extractUsername(jwt);
            if (username == null) {
                filterChain.doFilter(request, response);
                return;
            }
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (userDetails == null || !jwtService.isTokenValid(jwt, userDetails)) {
                filterChain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()  // Исправлено!
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (JwtException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
            return;
        }

        filterChain.doFilter(request, response);
    }
    private String getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) { // Имя вашей куки
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getJwtRefreshFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtr".equals(cookie.getName())) { // Имя вашей куки
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

