package com.web.museum.service;

import com.web.museum.dao.AchievementRepository;
import com.web.museum.entity.Achievement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    private AchievementRepository achievementRepository ;

    @Override
    public Page<Achievement> getAllAchievements(Pageable pageable) {
        return achievementRepository.findAll(pageable);
    }

    @Override
    public Achievement getAchievementById(int id) {
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (!optionalAchievement.isPresent()) { // hoặc optionalAchievement.isEmpty() với Java 11+
            throw new RuntimeException("Achievement not found");
        }
    return optionalAchievement.get();
    }

    @Override
    @Transactional
    public Achievement createAchievement(Achievement Achievement) {
        // Kiểm tra các trường bắt buộc
        if (Achievement.getName() == null || Achievement.getName().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        if (Achievement.getDescription() == null || Achievement.getDescription().isEmpty()) {
            throw new RuntimeException("Description is required");
        }

        Achievement achievement = new Achievement();
        achievement.setName(Achievement.getName());
        achievement.setDescription(Achievement.getDescription());

        Achievement savedAchievement = achievementRepository.save(achievement);
        return savedAchievement;
    }

    @Override
    public void deleteAchievement(int id) {
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (!optionalAchievement.isPresent()) { // hoặc optionalAchievement.isEmpty() với Java 11+
            throw new RuntimeException("Achievement not found");
        }
        Achievement achievement = optionalAchievement.get();
        achievementRepository.deleteById(achievement.getId());
    }

    @Override
    @Transactional
    public Achievement updateAchievement(int id, Achievement reqAchievement) {

        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if(!optionalAchievement.isPresent()) {
            throw new RuntimeException("Achievement not found!");
        }
        Achievement achievement = optionalAchievement.get();
        if (reqAchievement.getName() == null || reqAchievement.getName().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        if (reqAchievement.getDescription() == null || reqAchievement.getDescription().isEmpty()) {
            throw new RuntimeException("Description is required");
        }

        achievement.setName(reqAchievement.getName());
        achievement.setDescription(reqAchievement.getDescription());

        Achievement updatedAchievement = achievementRepository.saveAndFlush(achievement);

        return updatedAchievement;
    }
}
