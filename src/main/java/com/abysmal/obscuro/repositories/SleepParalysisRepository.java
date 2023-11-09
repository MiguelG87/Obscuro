package com.abysmal.obscuro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abysmal.obscuro.models.SleepParalysis;
import com.abysmal.obscuro.models.User;

public interface SleepParalysisRepository extends JpaRepository <SleepParalysis, Long> {
    SleepParalysis findByUser(User user);
    List<SleepParalysis> findTop5ByOrderByLevelDesc();
    List<SleepParalysis> findTop3ByOrderByLevelDesc();
}
