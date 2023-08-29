package com.example.Note.feature.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AuthService {
    public boolean hasAuthority(String role) {
        Collection<GrantedAuthority> authorities = getUser().getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(it -> it.equals(role));
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    private User getUser() {
        return (User) getAuthentication().getPrincipal();
    }

    private Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }
}
