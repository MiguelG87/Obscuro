package com.abysmal.obscuro.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.abysmal.obscuro.models.GameSettings;
import com.abysmal.obscuro.models.User;
import com.abysmal.obscuro.repositories.GameSettingsRepository;
import com.abysmal.obscuro.repositories.UserRepository;

@Controller
public class HomeController {

    private final UserRepository userDao;
    private final GameSettingsRepository gameSettingsDao;

    public HomeController(UserRepository userDao, GameSettingsRepository gameSettingsDao) {
        this.userDao = userDao;
        this.gameSettingsDao = gameSettingsDao;
    }

    @GetMapping("/")
    public String home() {
        return "landing";
    }

    @GetMapping("/home")
    public String homeUser(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/load/main")
    public String loadMain(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        return "loadMain";
    }

    @GetMapping("/main")
    public String main(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        return "main";
    }
}
