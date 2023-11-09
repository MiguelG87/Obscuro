package com.abysmal.obscuro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abysmal.obscuro.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
    boolean isUsernameTaken(String username);
}

