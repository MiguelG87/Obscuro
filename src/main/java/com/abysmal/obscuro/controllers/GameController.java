package com.abysmal.obscuro.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.abysmal.obscuro.models.GameSettings;
import com.abysmal.obscuro.models.Nightmare;
import com.abysmal.obscuro.models.SleepParalysis;
import com.abysmal.obscuro.models.SweetDreams;
import com.abysmal.obscuro.models.User;
import com.abysmal.obscuro.repositories.GameSettingsRepository;
import com.abysmal.obscuro.repositories.NightmareRepository;
import com.abysmal.obscuro.repositories.SleepParalysisRepository;
import com.abysmal.obscuro.repositories.SweetDreamsRepository;
import com.abysmal.obscuro.repositories.UserRepository;

@Controller
public class GameController {

    private final UserRepository userDao;
    private final SweetDreamsRepository sweetDreamsDao;
    private final NightmareRepository nightmareDao;
    private final SleepParalysisRepository sleepParalysisDao;
    private final GameSettingsRepository gameSettingsDao;

    public GameController(UserRepository userDao, SweetDreamsRepository sweetDreamsDao, NightmareRepository nightmareDao, SleepParalysisRepository sleepParalysisDao, GameSettingsRepository gameSettingsDao) {
        this.sweetDreamsDao = sweetDreamsDao;
        this.userDao = userDao;
        this.nightmareDao = nightmareDao;
        this.sleepParalysisDao = sleepParalysisDao;
        this.gameSettingsDao = gameSettingsDao;
    }

    @GetMapping("/load/sweetdreams")
    public String loadSweetDreams(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        return "loadSweetDreams";
    }
    
    @GetMapping("/sweetdreams")
    public String sweetDreams(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        SweetDreams sweetDreams = sweetDreamsDao.findByUser(user);
        List<SweetDreams> sweetDreamsTop = sweetDreamsDao.findTop5ByOrderByLevelDesc();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("sweetdreamstop", sweetDreamsTop);
        model.addAttribute("user", user);
        model.addAttribute("sweetdreams", sweetDreams);
        return "sweetDreams";
    }

    @PostMapping("/sweetdreams/{id}/level")
    public String sweetDreamsLevelUp(@PathVariable long id, @ModelAttribute SweetDreams newSweetDreams, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newSweetDreams.setUser(user);
        newSweetDreams.setId(id);
        sweetDreamsDao.save(newSweetDreams);
        SweetDreams sweetDreams = sweetDreamsDao.findByUser(user);
        List<SweetDreams> sweetDreamsTop = sweetDreamsDao.findTop5ByOrderByLevelDesc();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("sweetdreamstop", sweetDreamsTop);
        model.addAttribute("user", user);
        model.addAttribute("sweetdreams", sweetDreams);
        return "sweetDreamsLost";
    }

    @GetMapping("/load/nightmare")
    public String loadNightmare(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        return "loadNightmare";
    }

    @GetMapping("/nightmare")
    public String nightmare(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        Nightmare nightmare = nightmareDao.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("nightmare", nightmare);
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        return "nightmare";
    }

    @PostMapping("/nightmare/{id}/level")
    public String nightmareLevelUp(@PathVariable long id, @ModelAttribute Nightmare newNightmare, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newNightmare.setUser(user);
        newNightmare.setId(id);
        nightmareDao.save(newNightmare);
        Nightmare nightmare = nightmareDao.findByUser(user);
        List<Nightmare> nightmareTop = nightmareDao.findTop5ByOrderByLevelDesc();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("nightmaretop", nightmareTop);
        model.addAttribute("user", user);
        model.addAttribute("nightmare", nightmare);
        return "nightmareLost";
    }


    @GetMapping("/load/sleepparalysis")
    public String loadSleepParalysis(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        return "loadSleepParalysis";
    }

    @GetMapping("/sleepparalysis")
    public String sleepParalysis(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        SleepParalysis sleepParalysis = sleepParalysisDao.findByUser(user);
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("user", user);
        model.addAttribute("sleepparalysis", sleepParalysis);
        return "sleepParalysis";
    }

    @PostMapping("/sleepparalysis/{id}/level")
    public String sleepParalysisLevelUp(@PathVariable long id, @ModelAttribute SleepParalysis newSleepParalysis, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SleepParalysis sleepParalysis = sleepParalysisDao.findByUser(user);
        newSleepParalysis.setUser(user);
        newSleepParalysis.setId(id);
        sleepParalysisDao.save(newSleepParalysis);
        List<SleepParalysis> sleepParalysisTop = sleepParalysisDao.findTop5ByOrderByLevelDesc();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("sleepparalysistop", sleepParalysisTop);
        model.addAttribute("sleepparalysis", sleepParalysis);
        model.addAttribute("user", user);
        return "sleepParalysisLost";
    }

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        List<SweetDreams> sweetDreamsTop = sweetDreamsDao.findTop5ByOrderByLevelDesc();
        model.addAttribute("sweetdreamstop", sweetDreamsTop);
        List<Nightmare> nightmareTop = nightmareDao.findTop5ByOrderByLevelDesc();
        model.addAttribute("nightmaretop", nightmareTop);
        List<SleepParalysis> sleepParalysisTop = sleepParalysisDao.findTop5ByOrderByLevelDesc();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("sleepparalysistop", sleepParalysisTop);
        model.addAttribute("users", userDao.findAll());
        return "leaderboard";
    }

    
}
