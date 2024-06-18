package org.example.hirehub.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.hirehub.models.AccountModel;
import org.example.hirehub.models.ProfileModel;
import org.example.hirehub.services.AccountService;
import org.example.hirehub.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(HttpServletRequest request, Model model) {
        model.addAttribute("account", new AccountModel());
        return "register";
    }



    @PostMapping("/register")
    public String registerAccount(@ModelAttribute("account") AccountModel account, Model model) {
        AccountModel savedAccount = accountService.saveAccount(account);
        ProfileModel profile = new ProfileModel();
        profile.setUser(savedAccount);
        profileService.saveProfile(profile);
        model.addAttribute("profile", profile);
        return "redirect:/profile/edit";
    }
}
