package com.myshop.internetshop.classes.config;

import com.myshop.internetshop.classes.services.CustomUserDetailsService;
import com.myshop.internetshop.classes.services.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

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
}

