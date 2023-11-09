package com.abysmal.obscuro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abysmal.obscuro.models.Nightmare;
import com.abysmal.obscuro.models.User;

public interface NightmareRepository extends JpaRepository <Nightmare, Long> {
    Nightmare findByUser(User user);
    List<Nightmare> findTop5ByOrderByLevelDesc();
    List<Nightmare> findTop3ByOrderByLevelDesc();
}
