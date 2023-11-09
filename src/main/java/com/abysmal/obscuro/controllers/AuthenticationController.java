package com.abysmal.obscuro.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abysmal.obscuro.models.GameSettings;
import com.abysmal.obscuro.models.User;
import com.abysmal.obscuro.repositories.GameSettingsRepository;

@Controller
public class AuthenticationController {

    private final GameSettingsRepository gameSettingsDao;

    public AuthenticationController(GameSettingsRepository gameSettingsDao) {
        this.gameSettingsDao = gameSettingsDao;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/load/login")
    public String loadLogin(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        return "loadLogin";
    }

    @GetMapping("/load/logout")
    public String loadLogout(Model model) {
        return "loadLogout";
    }
}
