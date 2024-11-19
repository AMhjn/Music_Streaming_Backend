package com.music_streaming.middlewares;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = validateAndExtractUsername(token); // Implement JWT validation logic

            if (username != null) {
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(username));
            }
        }

        filterChain.doFilter(request, response);
    }

    private String validateAndExtractUsername(String token) {
        // Implement your JWT token validation logic here.
        return "extractedUsername"; // Replace this with actual username extraction
    }
}
