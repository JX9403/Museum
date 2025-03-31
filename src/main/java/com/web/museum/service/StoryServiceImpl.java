package com.web.museum.service;

import com.web.museum.dao.AuthorRepository;
import com.web.museum.dao.UserRepository;
import com.web.museum.dao.StoryRepository;
import com.web.museum.dto.AuthorInfoDTO;
import com.web.museum.dto.UserInfoDTO;
import com.web.museum.dto.StoryInfoDTO;
import com.web.museum.dto.StoryResponseDTO;
import com.web.museum.entity.Author;
import com.web.museum.entity.User;
import com.web.museum.entity.Story;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class StoryServiceImpl implements StoryService{
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<StoryInfoDTO> getAllStories(Pageable pageable) {
        Page<Story> stories = storyRepository.findAll(pageable);
        return stories.map(this::convertToInfoDTO);
    }

    @Override
    public StoryResponseDTO getStoryById(int id) {
        Optional<Story> storyOptional = storyRepository.findById(id);
        if(storyOptional.isEmpty()){
            throw new RuntimeException("Story not found");
        }
        Story story = storyOptional.get();

        story.setViews(story.getViews()+1);
        storyRepository.save(story);

        return convertToDTO(story);
    }

    @Override
    @Transactional
    public StoryResponseDTO updateStory(int id, Story reqStory) {

        Optional<Story> storyOptional = storyRepository.findById(id);
        if (storyOptional.isEmpty()) {
            throw new RuntimeException("Story not found");
        }
        Story story = storyOptional.get();

        if (reqStory.getTitle() == null || reqStory.getTitle().isEmpty()) {
            throw new RuntimeException("Title cannot be empty");
        }
        if (reqStory.getContent() == null || reqStory.getContent().isEmpty()) {
            throw new RuntimeException("Content cannot be empty");
        }

        if (reqStory.getStatus() == null) {
            throw new RuntimeException("Status cannot be empty");
        }

        if (reqStory.getAuthor() == null) {
            throw new RuntimeException("Valid Author ID is required");
        }

        // Lấy Author từ database
        Optional<Author> authorOptional = authorRepository.findById(reqStory.getAuthor().getId());
        if (authorOptional.isEmpty()) {
            throw new RuntimeException("Author not found");
        }

        // Kiểm tra và cập nhật User nếu có
        if (reqStory.getUser() != null) {
            Optional<User> userOptional = userRepository.findById(reqStory.getUser().getId());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            story.setUser(userOptional.get());
        } else {
            story.setUser(null);
        }

        story.setTitle(reqStory.getTitle());
        story.setContent(reqStory.getContent());
        story.setAuthor(authorOptional.get());
        story.setStatus(reqStory.getStatus());

        Story updatedStory = storyRepository.save(story);
        return convertToDTO(updatedStory);
    }


    @Override
    @Transactional
    public void deleteStory(int id) {
        Optional<Story> storyOptional = storyRepository.findById(id);
        if(storyOptional.isEmpty()){
            throw new RuntimeException("Story not found");
        }
        Story story = storyOptional.get();
        storyRepository.delete(story);
    }

    @Override
    @Transactional
    public StoryResponseDTO createStory(Story reqStory) {

        System.out.println("content :" + reqStory.getContent());
        if (reqStory.getTitle() == null || reqStory.getTitle().isEmpty()) {
            throw new RuntimeException("Title cannot be empty");
        }
        if (reqStory.getContent() == null || reqStory.getContent().isEmpty()) {
            throw new RuntimeException("Content cannot be empty");
        }
        if (reqStory.getAuthor() == null || reqStory.getAuthor().getId() == 0) {
            throw new RuntimeException("Valid Author ID is required");
        }
        if (reqStory.getStatus() == null) {
            throw new RuntimeException("Status cannot be empty");
        }

        // Tìm Author trong database trước khi gán vào Story
        Optional<Author> authorOptional = authorRepository.findById(reqStory.getAuthor().getId());
        if (authorOptional.isEmpty()) {
            throw new RuntimeException("Author not found");
        }

        // Kiểm tra và cập nhật User nếu có
        if (reqStory.getUser() != null) {
            Optional<User> userOptional = userRepository.findById(reqStory.getUser().getId());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            reqStory.setUser(userOptional.get());
        } else {
            reqStory.setUser(null);
        }
        reqStory.setAuthor(authorOptional.get());
        Story newStory = storyRepository.save(reqStory);
        return convertToDTO(newStory);
    }



    private StoryResponseDTO convertToDTO(Story story) {
        StoryResponseDTO StoryResponseDTO = new StoryResponseDTO();
        StoryResponseDTO.setId(story.getId());
        StoryResponseDTO.setTitle(story.getTitle());
        StoryResponseDTO.setContent(story.getContent());
        StoryResponseDTO.setStatus(story.getStatus());
        AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO();
        authorInfoDTO.setId(story.getAuthor().getId());
        authorInfoDTO.setName(story.getAuthor().getName());
        if(story.getUser() != null){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(story.getUser().getId());
            userInfoDTO.setFullname(story.getUser().getFullname());
            StoryResponseDTO.setUser(userInfoDTO);
        }
        else {
            StoryResponseDTO.setUser(null);
        }

        StoryResponseDTO.setAuthor(authorInfoDTO);

        return StoryResponseDTO;
    }

    private StoryInfoDTO convertToInfoDTO(Story story) {
        StoryInfoDTO StoryInfoDTO = new StoryInfoDTO();
        StoryInfoDTO.setId(story.getId());
        StoryInfoDTO.setTitle(story.getTitle());
        StoryInfoDTO.setViews(story.getViews());
        StoryInfoDTO.setSaves(story.getSaves());
        StoryInfoDTO.setStatus(story.getStatus());
        AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO();
        authorInfoDTO.setId(story.getAuthor().getId());
        authorInfoDTO.setName(story.getAuthor().getName());
        if(story.getUser() != null){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(story.getUser().getId());
            userInfoDTO.setFullname(story.getUser().getFullname());
            StoryInfoDTO.setUser(userInfoDTO);
        }
        else {
            StoryInfoDTO.setUser(null);
        }

        StoryInfoDTO.setAuthor(authorInfoDTO);

        return StoryInfoDTO;
    }

}
