package com.example.demo.auth.controller;

import com.example.demo.auth.service.UserDetailsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserDetailsBean userService;

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }
}
