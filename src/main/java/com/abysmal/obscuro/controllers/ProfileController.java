package com.abysmal.obscuro.controllers;

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

@Controller
public class ProfileController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
    private final SweetDreamsRepository sweetDreamsDao;
    private final NightmareRepository nightmareDao;
    private final SleepParalysisRepository sleepParalysisDao;
    private final GameSettingsRepository gameSettingsDao;
    private final UserService userService;

    public ProfileController(UserRepository userDao, PasswordEncoder passwordEncoder, SweetDreamsRepository sweetDreamsDao, NightmareRepository nightmareDao, SleepParalysisRepository sleepParalysisDao, GameSettingsRepository gameSettingsDao, UserService userService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.sweetDreamsDao = sweetDreamsDao;
        this.nightmareDao = nightmareDao;
        this.sleepParalysisDao = sleepParalysisDao;
        this.gameSettingsDao = gameSettingsDao;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
public String submitRegistrationForm(@ModelAttribute User user, Model model) {
    String password = user.getPassword();
    if (password == null || password.trim().isEmpty()) {
        model.addAttribute("user", new User());
        model.addAttribute("passwordError", "Password cannot be empty.");
        return "register";
    }
    if (userDao.isUsernameTaken(user.getUsername())) {
        model.addAttribute("user", new User());
        model.addAttribute("usernameError", "Username is already taken");
        return "register";
    }
    String patternPW = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
    Pattern pattern = Pattern.compile(patternPW);
    Matcher matcher = pattern.matcher(password);
    if (!matcher.matches()) {
        model.addAttribute("user", new User());
        model.addAttribute("passwordError", "Your password is not between 8-16 characters or does not meet the criteria.");
        return "register";
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    String imageUrl = "/img/image.png";
    user.setProfilePicture(imageUrl);
    userDao.save(user);
    SweetDreams sweetDreams = new SweetDreams();
    sweetDreams.setUser(user);
    sweetDreams.setLevel("0");
    sweetDreamsDao.save(sweetDreams);
    Nightmare nightmare = new Nightmare();
    nightmare.setUser(user);
    nightmare.setLevel("0");
    nightmareDao.save(nightmare);
    SleepParalysis sleepParalysis = new SleepParalysis();
    sleepParalysis.setUser(user);
    sleepParalysis.setLevel("0");
    sleepParalysisDao.save(sleepParalysis);
    GameSettings gameSettings = new GameSettings();
    gameSettings.setUser(user);
    gameSettings.setBrightness(01.00);
    gameSettings.setGameMusic(00.20);
    gameSettings.setHomeMusic(00.20);
    gameSettings.setSoundFX(00.25);
    gameSettingsDao.save(gameSettings);
    return "loadRegister";
}

@GetMapping("/profile")
public String profile(Model model) {
    User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userDao.findById(loggedInUser.getId()).orElseThrow(); // Use orElseThrow() for a safer approach
    SweetDreams sweetDreams = sweetDreamsDao.findByUser(user);
    Nightmare nightmare = nightmareDao.findByUser(user);
    SleepParalysis sleepParalysis = sleepParalysisDao.findByUser(user);
    GameSettings gameSettings = gameSettingsDao.findByUser(user);
    // Get the user's position in the sweetDreamsTop list
    Optional<Integer> userPositionInSweetDreams = userService.getUserPlacement(user);
    Optional<Integer> userPositionInNightmare = userService.getUserPlacementNightmare(user);
    Optional<Integer> userPositionInSleepParalysis = userService.getUserPlacementSleepParalysis(user);
    // If the user is not in the top 3, default position to 0
    int userPosition = userPositionInSweetDreams.orElse(0);
    int userPositionNightmare = userPositionInNightmare.orElse(0);
    int userPositionSleepParlysis = userPositionInSleepParalysis.orElse(0);
    model.addAttribute("gamesettings", gameSettings);
    model.addAttribute("user", user);
    model.addAttribute("sweetdreams", sweetDreams);
    model.addAttribute("nightmare", nightmare);
    model.addAttribute("sleepparalysis", sleepParalysis);
    // Add the user's position in sweetDreamsTop to the model
    model.addAttribute("userPositionInSweetDreams", userPosition);
    model.addAttribute("userPositionInNightmare", userPositionNightmare);
    model.addAttribute("userPositionInSleepParalysis", userPositionSleepParlysis);
    return "profile";
}

    @GetMapping("/profile/{id}")
    public String profileUser(@PathVariable long id, Model model) {
        User user = userDao.findById(id).get();
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
        return "profile";
    }
}