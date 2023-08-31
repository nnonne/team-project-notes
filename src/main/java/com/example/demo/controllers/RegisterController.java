package com.example.demo.controllers;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final UserDetailsServiceImpl userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserDetailsServiceImpl userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "register";
        } else if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("usernameExistsError", "This username was taken by another user.");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return "redirect:/login";
    }
}
