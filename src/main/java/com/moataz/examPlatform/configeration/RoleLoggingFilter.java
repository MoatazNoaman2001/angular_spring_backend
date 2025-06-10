package com.moataz.examPlatform.configeration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RoleLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RoleLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            log.debug("Request: {}, User: {}, Roles: {}",
                    request.getRequestURI(),
                    authentication.getName(),
                    authentication.getAuthorities());
        } else {
            log.debug("Request: {}, No authenticated user", request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }
}
