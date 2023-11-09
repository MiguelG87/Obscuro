package com.abysmal.obscuro.config;

import com.abysmal.obscuro.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
// Will allow us to edit the MVC security for our application
@EnableWebSecurity
public class SecurityConfiguration {
    public SecurityConfiguration(UserDetailsLoader usersLoader) {
    }


    // The @Bean annotation means that the class itself is being managed by Spring.

    // Is a class that is managed by Spring, specifically to hash and unhash our User passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This class is used to manage the users Authentication status.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // This class will provide filters for our Spring security for different URL mappings.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        /* Pages that require authentication
                         * only authenticated users can edit and delete profiles! */


                        .requestMatchers(
                                "/profile",
                                "/home",
                                "/settings/*/profile",
                                "/settings/*/game",
                                "/settings/*/delete",
                                "/settings/*/account",
                                "/settings/*/account/password",
                                "/settings",
                                "/sweetdreams/*/level",
                                "/nightmare/*/level",
                                "/sleepparalysis/*/level",
                                "/leaderboard",
                                "/sleepparalysis",
                                "/sweetdreams",
                                "/load/sweetdreams",
                                "/load/nightmare",
                                "/load/sleepparalysis",
                                "/nightmare"
                                ).authenticated()

                        /* Pages that do not require authentication
                         * anyone can visit the home page, register, login, game settings and view leaderboard */


                        .requestMatchers("/",
                        "/profile/{id}",
                                "/about",
                                "/register",
                                "/main",
                                "/load/main",
                                "/load/logout",
                                "/load/login",
                                "/login",
                                "/logout",
                                "/css/scary.jpg"
                                ).permitAll()

                        // allow loading of static resources
                        .requestMatchers("/css/**", "/js/**", "/img/**","/keys.js", "/favicon.ico", "/vid/**", "/audio/**").permitAll()
                )
                /* Login configuration */
                .formLogin((login) -> login.loginPage("/login").defaultSuccessUrl("/load/login", true))
                /* Logout configuration */
                .logout((logout) -> logout.logoutSuccessUrl("/load/logout"))
                .httpBasic(withDefaults());
        return http.build();
    }
}