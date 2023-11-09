package com.abysmal.obscuro.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abysmal.obscuro.models.Nightmare;
import com.abysmal.obscuro.models.SleepParalysis;
import com.abysmal.obscuro.models.SweetDreams;
import com.abysmal.obscuro.models.User;
import com.abysmal.obscuro.repositories.NightmareRepository;
import com.abysmal.obscuro.repositories.SleepParalysisRepository;
import com.abysmal.obscuro.repositories.SweetDreamsRepository;

@Service
public class UserService {

    @Autowired
    private SweetDreamsRepository sweetDreamsRepository;
    @Autowired
    private NightmareRepository nightmareRepository;
    @Autowired
    private SleepParalysisRepository sleepParalysisRepository;

    public Optional<Integer> getUserPlacement(User user) {
        // Get the top 3 SweetDreams entities ordered by level
        List<SweetDreams> topSweetDreams = sweetDreamsRepository.findTop3ByOrderByLevelDesc();
        // Extract user IDs from the SweetDreams entities
        List<Long> topUserIds = topSweetDreams.stream()
                .map(sweetDreams -> sweetDreams.getUser().getId())
                .collect(Collectors.toList());
        // Find the index of the user in the topUserIds list
        int placement = topUserIds.indexOf(user.getId()); // user.getId() is a Long
        // If the user is not in the top 3, return Optional.empty()
        return placement != -1 ? Optional.of(placement + 1) : Optional.empty();
    }

    public Optional<Integer> getUserPlacementNightmare(User user) {
        List<Nightmare> topNightmare = nightmareRepository.findTop3ByOrderByLevelDesc();
        List<Long> topUserIds = topNightmare.stream()
                .map(nightmare -> nightmare.getUser().getId())
                .collect(Collectors.toList());
        int placement = topUserIds.indexOf(user.getId());
        return placement != -1 ? Optional.of(placement + 1) : Optional.empty();
    }

    public Optional<Integer> getUserPlacementSleepParalysis(User user) {
        List<SleepParalysis> topSleepParalysis = sleepParalysisRepository.findTop3ByOrderByLevelDesc();
        // Extract user IDs from the SweetDreams entities
        List<Long> topUserIds = topSleepParalysis.stream()
                .map(sleepParalysis -> sleepParalysis.getUser().getId())
                .collect(Collectors.toList());
        // Find the index of the user in the topUserIds list
        int placement = topUserIds.indexOf(user.getId()); // user.getId() is a Long
        // If the user is not in the top 3, return Optional.empty()
        return placement != -1 ? Optional.of(placement + 1) : Optional.empty();
    }
}



