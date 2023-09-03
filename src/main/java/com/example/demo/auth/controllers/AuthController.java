package com.example.demo.auth.controllers;

import com.example.demo.auth.domain.User;
import com.example.demo.auth.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

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

    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            User currentUser = userDetailsService.findByUsername(username);

            if (passwordEncoder.matches(password, currentUser.getPassword())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "redirect:/note/list/" + currentUser.getId();
            } else {
                model.addAttribute("error", "Incorrect login or password.");
                return "login";
            }
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "User not found.");
            return "login";
        }
    }

    private boolean checkAuthenticationStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
