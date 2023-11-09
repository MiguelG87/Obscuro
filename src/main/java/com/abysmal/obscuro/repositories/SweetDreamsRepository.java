package com.abysmal.obscuro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abysmal.obscuro.models.SweetDreams;
import com.abysmal.obscuro.models.User;

public interface SweetDreamsRepository extends JpaRepository <SweetDreams, Long> {
    SweetDreams findByUser(User user);
    List<SweetDreams> findTop5ByOrderByLevelDesc();
    List<SweetDreams> findTop3ByOrderByLevelDesc();
}
