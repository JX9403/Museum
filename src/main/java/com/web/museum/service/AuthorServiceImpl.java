package com.web.museum.service;

import com.web.museum.dao.AchievementRepository;
import com.web.museum.dao.AuthorRepository;
import com.web.museum.dao.ImageRepository;
import com.web.museum.dto.AchievementInfoDTO;
import com.web.museum.dto.AuthorResponseDTO;
import com.web.museum.entity.Achievement;
import com.web.museum.entity.Author;
import com.web.museum.entity.Image;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository ;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private ImageRepository imageRepository;


    @Override
    public Page<AuthorResponseDTO> getAllAuthors(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable);
      return authors.map(this::convertToDTO) ;
    }

    @Override
    public AuthorResponseDTO getAuthorById(int id) {
        Optional<Author> authorOptional  = authorRepository.findById(id);
        if(authorOptional.isEmpty()) {
            throw new RuntimeException("Author not found");
        }
        Author author = authorOptional.get();

        return convertToDTO(author);
    }

    @Override
    @Transactional
    public AuthorResponseDTO createAuthor(Author reqAuthor) {
        // Kiểm tra các trường bắt buộc
        if (reqAuthor.getName() == null || reqAuthor.getName().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        if (reqAuthor.getBiography() == null || reqAuthor.getBiography().isEmpty()) {
            throw new RuntimeException("Biography is required");
        }
        if (reqAuthor.getCareer() == null || reqAuthor.getCareer().isEmpty()) {
            throw new RuntimeException("Career is required");
        }
        if (reqAuthor.getType() == null ) {
            throw new RuntimeException("AuthorType is required");
        }

        Author author = new Author();
        author.setName(reqAuthor.getName());
        author.setBirthYear(reqAuthor.getBirthYear());
        author.setDeathYear(reqAuthor.getDeathYear());
        author.setBiography(reqAuthor.getBiography());
        author.setCareer(reqAuthor.getCareer());
        author.setType(reqAuthor.getType());

        // Thêm danh sách giải thưởng nếu có
        List<Achievement> achievements = reqAuthor.getListAchievements();
        if (!achievements.isEmpty()) {
            author.setListAchievements(achievements);
        } else {
            author.setListAchievements(new ArrayList<>()); // Khởi tạo danh sách rỗng nếu không có giải thưởng
        }

        Author savedAuthor = authorRepository.save(author);
return  convertToDTO(savedAuthor);
    }

    @Override
    @Transactional
    public AuthorResponseDTO updateAuthor(int id, Author reqAuthor) {
        Optional<Author> authorOptional  = authorRepository.findById(id);
        if(authorOptional.isEmpty()) {
            throw new RuntimeException("Author not found");
        }
        Author author = authorOptional.get();
        if (reqAuthor.getName() == null || reqAuthor.getName().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        if (reqAuthor.getBirthYear() <= 0) {
            throw new RuntimeException("Invalid birth year");
        }
        if (reqAuthor.getBiography() == null || reqAuthor.getBiography().isEmpty()) {
            throw new RuntimeException("Biography is required");
        }
        if (reqAuthor.getType() == null ) {
            throw new RuntimeException("AuthorType is required");
        }

        author.setName(reqAuthor.getName());
        author.setBirthYear(reqAuthor.getBirthYear());
        author.setDeathYear(reqAuthor.getDeathYear());
        author.setBiography(reqAuthor.getBiography());
        author.setCareer(reqAuthor.getCareer());
        author.setType(reqAuthor.getType());

        if(reqAuthor.getListImages() != null){
            author.setListImages((reqAuthor.getListImages()));
        }

        // Kiểm tra và thay thế danh sách giải thưởng
        List<Achievement> achievements = reqAuthor.getListAchievements();
        if(achievements != null) {
            List<Achievement> updatedAchievements = new ArrayList<>();
            for (Achievement achievement : achievements) {
                Optional<Achievement> existAchievement = achievementRepository.findById(achievement.getId());

                if (existAchievement.isEmpty()) {
                    throw new RuntimeException("Achievement not found");
                }
                updatedAchievements.add(existAchievement.get());
            }

            author.setListAchievements(updatedAchievements);
        }
        authorRepository.save(author);
        return convertToDTO(author) ;
    }

    @Override
    @Transactional
    public void  deleteAuthor(int id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if(authorOptional == null) {
            throw new RuntimeException("Author not found!");
        }
        Author author = authorOptional.get();
        authorRepository.deleteById(id);
    }

    private AuthorResponseDTO convertToDTO(Author author) {
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setId(author.getId());
        authorResponseDTO.setName(author.getName());
        authorResponseDTO.setBirthYear(author.getBirthYear());
        authorResponseDTO.setDeathYear(author.getDeathYear());
        authorResponseDTO.setBiography(author.getBiography());
        authorResponseDTO.setCareer(author.getCareer());
        authorResponseDTO.setType(author.getType().toString());

        List<AchievementInfoDTO> achievementInfoDTOs = author.getListAchievements().stream()
                .map(achievement -> {
                    AchievementInfoDTO achievementInfoDTO = new AchievementInfoDTO();
                    achievementInfoDTO.setId(achievement.getId());
                    achievementInfoDTO.setName(achievement.getName());
                    return achievementInfoDTO;
                })
                .collect(Collectors.toList());

        authorResponseDTO.setListAchievements(achievementInfoDTOs);
        return authorResponseDTO;
    }
}
