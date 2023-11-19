package com.abysmal.obscuro.controllers;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.abysmal.obscuro.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SettingController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
    private final SweetDreamsRepository sweetDreamsDao;
    private final NightmareRepository nightmareDao;
    private final SleepParalysisRepository sleepParalysisDao;
    private final GameSettingsRepository gameSettingsDao;
    private final UserService userService;

    public SettingController(UserRepository userDao, PasswordEncoder passwordEncoder, SweetDreamsRepository sweetDreamsDao, NightmareRepository nightmareDao, SleepParalysisRepository sleepParalysisDao, GameSettingsRepository gameSettingsDao, UserService userService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.sweetDreamsDao = sweetDreamsDao;
        this.nightmareDao = nightmareDao;
        this.sleepParalysisDao = sleepParalysisDao;
        this.gameSettingsDao = gameSettingsDao;
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("user", user);
        return "settings/settings";
    }

    @GetMapping("/settings/{id}/game")
    public String settingsGame(@PathVariable long id, Model model) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (user.getId() != id) {
        throw new AccessDeniedException("You cannot edit other users' profile");
    }
    if (userDao.findById(id).isPresent()) {
        model.addAttribute("user", userDao.findById(id).get());
    }
        GameSettings gameSettings = gameSettingsDao.getByUserId(id);
        model.addAttribute("gamesettings", gameSettings);
        return "settings/settingsGame";
    }

    @PostMapping("/settings/{id}/game")
    public String settingsGameSubmit(@PathVariable long id, @ModelAttribute GameSettings editSettings, Model model) throws AccessDeniedException {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        if(user.getId() != id){
            throw new AccessDeniedException("You cannot edit other users' profile");
        }
        editSettings.setUser(user);
        gameSettingsDao.save(editSettings);
        GameSettings gameSettings = gameSettingsDao.getByUserId(id);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("user", userDao.findById(id).get());
        return "settings/loadSuccess";
    }

    @GetMapping("/settings/{id}/profile")
    public String settingsProfile(@PathVariable long id, Model model) throws AccessDeniedException {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (user.getId() != id) {
        throw new AccessDeniedException("You cannot edit other users' profile");
    }
    if (userDao.findById(id).isPresent()) {
        model.addAttribute("user", userDao.findById(id).get());
        
    }
    GameSettings gameSettings = gameSettingsDao.findByUser(user);
    model.addAttribute("FILESTACK_KEY", "AhDqzkZ1jR9aLMXnoEicMz");
    model.addAttribute("gamesettings", gameSettings);
    return "settings/settingsProfile";
}

    @PostMapping("/settings/{id}/profile")
    public String submitSettingsProfile(@PathVariable long id, @ModelAttribute User editProfile, Model model) throws AccessDeniedException {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        if(user.getId() != id){
            throw new AccessDeniedException("You cannot edit other users' profile");
        }
        if (!editProfile.getUsername().equals(user.getUsername()) && userDao.isUsernameTaken(editProfile.getUsername())) {
            GameSettings gameSettings = gameSettingsDao.findByUser(user);
            model.addAttribute("gamesettings", gameSettings);
            model.addAttribute("user", userDao.findById(id).get());
            model.addAttribute("usernameError", "Username is already taken");
            return "settings/settingsProfile";
        }
        userDao.save(editProfile);
        SweetDreams sweetDreams = sweetDreamsDao.findByUser(user);
        Nightmare nightmare = nightmareDao.findByUser(user);
        SleepParalysis sleepParalysis = sleepParalysisDao.findByUser(user);
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        Optional<Integer> userPositionInSweetDreams = userService.getUserPlacement(user);
        Optional<Integer> userPositionInNightmare = userService.getUserPlacementNightmare(user);
        Optional<Integer> userPositionInSleepParalysis = userService.getUserPlacementSleepParalysis(user);
        int userPosition = userPositionInSweetDreams.orElse(0);
        int userPositionNightmare = userPositionInNightmare.orElse(0);
        int userPositionSleepParlysis = userPositionInSleepParalysis.orElse(0);
        model.addAttribute("gamesettings", gameSettings);
        model.addAttribute("user", user);
        model.addAttribute("sweetdreams", sweetDreams);
        model.addAttribute("nightmare", nightmare);
        model.addAttribute("sleepparalysis", sleepParalysis);
        model.addAttribute("userPositionInSweetDreams", userPosition);
        model.addAttribute("userPositionInNightmare", userPositionNightmare);
        model.addAttribute("userPositionInSleepParalysis", userPositionSleepParlysis);
        return "settings/loadSuccess";
    }

    @GetMapping("/settings/{id}/account")
    public String settingsAccount(@PathVariable long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDao.findById(id).isPresent()){
            model.addAttribute("user", user);
            GameSettings gameSettings = gameSettingsDao.findByUser(user);
            model.addAttribute("gamesettings", gameSettings);
        }
        return "settings/settingsAccount";
    }

    @PostMapping("/settings/{id}/account/password")
    public String submitSettingsAccount (Model model, @ModelAttribute User user, @PathVariable long id, @RequestParam("password") String password, HttpSession session) throws AccessDeniedException {
        password = user.getPassword();
        User loggedInUser = userDao.findById(id).get();
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(loggedInUser.getId() != authorizedUser.getId()){
            throw new AccessDeniedException("You are not authorized to change password");
        }
        if (password == null || password.trim().isEmpty()) {
            model.addAttribute("user", user);
            GameSettings gameSettings = gameSettingsDao.findByUser(user);
            model.addAttribute("gamesettings", gameSettings);
            model.addAttribute("passwordError", "Password cannot be empty.");
            return "settings/settingsAccount";
        }
        String patternPW = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
        Pattern pattern = Pattern.compile(patternPW);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            model.addAttribute("user", user);
            GameSettings gameSettings = gameSettingsDao.findByUser(user);
            model.addAttribute("gamesettings", gameSettings);
            model.addAttribute("passwordError", "Password must be between 8-16 characters, and include at least one upper case, one lower case, one digit, and one symbol.");
            return "settings/settingsAccount";
        }
        loggedInUser.setPassword(passwordEncoder.encode(password));
        System.out.println("New encoded password: "+loggedInUser.getPassword());
        GameSettings gameSettings = gameSettingsDao.findByUser(user);
        model.addAttribute("gamesettings", gameSettings);
        userDao.save(loggedInUser);
        session.invalidate();
        return "settings/loadPassword";
    }

    @PostMapping("/settings/{id}/delete")
    public String submitDelete (HttpSession session, @PathVariable long id) throws AccessDeniedException {
        User loggedInUser = userDao.findById(id).get();
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedInUser.getId() != authorizedUser.getId()){
            throw new AccessDeniedException("You cannot delete other user's account");
        }
        userDao.deleteById(id);
        session.invalidate();
        return "settings/loadDelete";
    }
}
