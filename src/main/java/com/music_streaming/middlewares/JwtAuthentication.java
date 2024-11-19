package com.music_streaming.middlewares;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication implements Authentication {

    private final String username;
    private boolean authenticated = true;

    public JwtAuthentication(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // You can return roles/authorities here if needed
    }

    @Override
    public Object getCredentials() {
        return null; // Not used for JWT-based authentication
    }

    @Override
    public Object getDetails() {
        return null; // Additional user details can go here
    }

    @Override
    public Object getPrincipal() {
        return username; // The username or identifier of the authenticated user
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username; // Typically the username of the authenticated user
    }
}
