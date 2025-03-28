package com.web.museum.service;
import com.web.museum.entity.Achievement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.security.PublicKey;

public interface AchievementService {

    public Page<Achievement>getAllAchievements(Pageable pageable );

    public Achievement  getAchievementById(int id);

    public Achievement  createAchievement(Achievement  achievementDTO);

    public void deleteAchievement(int id);

    public Achievement  updateAchievement(int id, Achievement  achievementDTO);

}
