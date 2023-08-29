package com.example.demo.auth.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String redirectTo() {
        boolean isAuthenticated = checkAuthenticationStatus();
        if (isAuthenticated) {
            return "redirect:/note/list";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    private boolean checkAuthenticationStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
