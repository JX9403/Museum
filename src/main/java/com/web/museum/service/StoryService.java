package com.web.museum.service;

import com.web.museum.dto.StoryInfoDTO;
import com.web.museum.dto.StoryResponseDTO;
import com.web.museum.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoryService {
    Page<StoryInfoDTO> getAllStories(Pageable pageable);
    StoryResponseDTO getStoryById ( int id ) ;
    StoryResponseDTO updateStory ( int id, Story story );
    void deleteStory ( int id);
    StoryResponseDTO createStory ( Story story );

}