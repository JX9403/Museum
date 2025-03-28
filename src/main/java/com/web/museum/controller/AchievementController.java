package com.web.museum.controller;

import com.web.museum.dto.PagedResponse;
import com.web.museum.entity.Achievement;
import com.web.museum.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping
    public ResponseEntity<PagedResponse<Achievement>> getAllAchievements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

           Pageable pageable = PageRequest.of(page, size);

           Page<Achievement> achievementPage = achievementService.getAllAchievements(pageable);
           PagedResponse<Achievement> response = new PagedResponse<>(
                   achievementPage.getContent(),
                   achievementPage.getNumber(),
                   achievementPage.getSize(),
                   achievementPage.getTotalElements(),
                   achievementPage.getTotalPages()
           );

           return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Achievement> createAchievement(@RequestBody Achievement Achievement) {
        Achievement createdAchievement = achievementService.createAchievement(Achievement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAchievement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Achievement> updateAchievement ( @PathVariable int id, @RequestBody Achievement reqAchievement) {
        Achievement updateAchievement = achievementService.updateAchievement(id,reqAchievement );
        return ResponseEntity.ok(updateAchievement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAchievement(@PathVariable int id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.ok("Achievement deleted successfully");
    }

}
