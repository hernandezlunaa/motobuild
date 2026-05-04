package com.motobuild.controller;

import com.motobuild.model.User;
import com.motobuild.service.AuthService;
import com.motobuild.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    public AuthController(AuthService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String showLogin(HttpSession session) {
        if (authService.isLoggedIn(session)) {
            return "redirect:/builds";
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            User user = authService.login(email, password);
            authService.loginSession(session, user);
            return "redirect:/";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegister(HttpSession session) {
        if (authService.isLoggedIn(session)) {
            return "redirect:/builds";
        }

        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = authService.register(firstName, lastName, email, password, confirmPassword);
            authService.loginSession(session, user);
            return "redirect:/";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

            redirectAttributes.addFlashAttribute("firstName", firstName);
            redirectAttributes.addFlashAttribute("lastName", lastName);
            redirectAttributes.addFlashAttribute("email", email);

            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "redirect:/login";
    }

    @GetMapping("/account")
    public String showAccount(HttpSession session, Model model) {
        User user = authService.getLoggedInUser(session);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("loggedInUser", user);
        model.addAttribute("accountStats", accountService.getAccountStats(user.getUserId()));
        model.addAttribute("recentActivity", accountService.getRecentActivity(user.getUserId()));

        return "account";
    }

    @PostMapping("/account/delete")
    public String deleteAccount(@RequestParam String password,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            authService.deleteAccount(session, password);
            redirectAttributes.addFlashAttribute("successMessage", "Your account has been deleted.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/account";
        }
    }

    @PostMapping("/account/update-profile")
    public String updateProfile(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String currentPassword,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            authService.updateProfile(session, firstName, lastName, email, currentPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully.");
            return "redirect:/account";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/account";
        }
    }

    @PostMapping("/account/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmNewPassword,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        try {
            authService.changePassword(session, currentPassword, newPassword, confirmNewPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully.");
            return "redirect:/account";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/account";
        }
    }
}