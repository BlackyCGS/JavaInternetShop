package com.myshop.internetshop.classes.config;

import com.myshop.internetshop.classes.services.VisitCounterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class VisitCounterFilter extends OncePerRequestFilter {

    private final VisitCounterService visitCounterService;

    @Autowired
    public VisitCounterFilter(VisitCounterService visitCounterService) {
        this.visitCounterService = visitCounterService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain)
            throws IOException, ServletException {
        if (request.getRequestURI().startsWith("/api/")) {
            if (request.getRequestURI().startsWith("/api/visits/")) {
                filterChain.doFilter(request, response);
                return;
            }
            visitCounterService.incrementUrlVisits(request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}

