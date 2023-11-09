package com.abysmal.obscuro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abysmal.obscuro.models.GameSettings;
import com.abysmal.obscuro.models.User;

public interface GameSettingsRepository extends JpaRepository <GameSettings, Long>{
    GameSettings getByUserId(long id);
    GameSettings findByUser(User user);
}
