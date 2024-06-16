package org.example.hirehub.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.hirehub.models.AccountModel;
import org.example.hirehub.services.AccountService;
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

//    @GetMapping("/login")
//    public String showLoginForm(HttpServletRequest request, Model model) {
//        model.addAttribute("request", request);
//        return "login";
//    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(HttpServletRequest request, Model model) {
        model.addAttribute("account", new AccountModel());
        model.addAttribute("request", request);
        return "register";
    }


    @PostMapping("/register")
    public String registerAccount(@ModelAttribute("account") AccountModel account) {
        accountService.saveAccount(account);
        return "redirect:/login";
    }
}