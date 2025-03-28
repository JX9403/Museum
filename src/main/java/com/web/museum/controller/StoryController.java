package com.web.museum.controller;

import com.web.museum.dto.PagedResponse;
import com.web.museum.dto.StoryInfoDTO;
import com.web.museum.dto.StoryResponseDTO;
import com.web.museum.entity.Story;
import com.web.museum.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/stories")
public class StoryController {
    @Autowired
    private StoryService storyService;

    @GetMapping
    public ResponseEntity<PagedResponse<StoryInfoDTO>> getAllStorys(
            @RequestParam int page,
            @RequestParam int size ){
        Pageable pageable = PageRequest.of(page, size);
        Page<StoryInfoDTO> stories = storyService.getAllStories(pageable);

        PagedResponse<StoryInfoDTO> response = new PagedResponse<>(
                stories.getContent(),
                stories.getNumber(),
                stories.getSize(),
                stories.getTotalElements(),
                stories.getTotalPages()
        );

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryResponseDTO> getStoryById (@PathVariable int id) {
        StoryResponseDTO storyResponseDTO = storyService.getStoryById(id);
        return ResponseEntity.ok(storyResponseDTO);
    }

    @PostMapping
    public ResponseEntity<StoryResponseDTO> createStory ( @RequestBody Story story){
        StoryResponseDTO storyResponseDTO = storyService.createStory(story);
        return ResponseEntity.ok(storyResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryResponseDTO> updateStory(@PathVariable int id, @RequestBody Story reqStory){
        StoryResponseDTO storyResponseDTO = storyService.updateStory(id,reqStory);
        return ResponseEntity.ok(storyResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable int id ){
        storyService.deleteStory(id);
        return ResponseEntity.ok("Success delete story id = " + "id");
    }
}
