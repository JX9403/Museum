package com.web.museum.service;

import com.web.museum.dao.AchievementRepository;
import com.web.museum.dao.AuthorRepository;
import com.web.museum.dao.ImageRepository;
import com.web.museum.dto.AchievementInfoDTO;
import com.web.museum.dto.AuthorResponseDTO;
import com.web.museum.dto.ImageInfoDTO;
import com.web.museum.entity.Achievement;
import com.web.museum.entity.Author;
import com.web.museum.entity.Image;
import com.web.museum.util.AuthorType;
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
    public Page<AuthorResponseDTO> getAllAuthors(String name, String type, String sort, Pageable pageable) {
        AuthorType authorType = null;
        if (type != null && !type.isBlank()) {
            authorType = AuthorType.valueOf(type.toUpperCase());
        }

        Page<Author> authors = authorRepository.searchAuthors(
                name == null || name.isBlank() ? null : name,
                authorType,
                pageable
        );

        return authors.map(this::convertToDTO);
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

        // Thêm danh sách hình ảnh nếu có
        List<Image> images = reqAuthor.getListImages();
        if (images != null && !images.isEmpty()) {
            for (Image img : images) {
                img.setAuthor(author);
            }
            author.setListImages(images);
        } else {
            author.setListImages(new ArrayList<>());
        }

        Author savedAuthor = authorRepository.save(author);
        return  convertToDTO(savedAuthor);
    }

    @Override
    @Transactional
    public AuthorResponseDTO updateAuthor(int id, Author reqAuthor) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            throw new RuntimeException("Author not found");
        }
        Author author = authorOptional.get();

        // Kiểm tra các trường dữ liệu của tác giả
        if (reqAuthor.getName() == null || reqAuthor.getName().isEmpty()) {
            throw new RuntimeException("Name is required");
        }
        if (reqAuthor.getBirthYear() <= 0) {
            throw new RuntimeException("Invalid birth year");
        }
        if (reqAuthor.getBiography() == null || reqAuthor.getBiography().isEmpty()) {
            throw new RuntimeException("Biography is required");
        }
        if (reqAuthor.getType() == null) {
            throw new RuntimeException("AuthorType is required");
        }

        // Cập nhật thông tin cơ bản của tác giả
        author.setName(reqAuthor.getName());
        author.setBirthYear(reqAuthor.getBirthYear());
        author.setDeathYear(reqAuthor.getDeathYear());
        author.setBiography(reqAuthor.getBiography());
        author.setCareer(reqAuthor.getCareer());
        author.setType(reqAuthor.getType());

        // Cập nhật danh sách ảnh
        if (reqAuthor.getListImages() != null) {
            // Lặp qua danh sách ảnh và kiểm tra URL ảnh
            List<Image> validImages = new ArrayList<>();
            for (Image image : reqAuthor.getListImages()) {
                if (image.getUrl() != null && !image.getUrl().isEmpty()) {
                    // Nếu URL hợp lệ, gắn thêm authorId vào ảnh
                    image.setAuthor(author); // Gắn đúng authorId vào ảnh
                    validImages.add(image);
                }
            }
            // Cập nhật danh sách ảnh cho tác giả
            author.setListImages(validImages);
        }

        // Cập nhật danh sách giải thưởng
        List<Achievement> achievements = reqAuthor.getListAchievements();
        if (achievements != null) {
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

        // Lưu thông tin tác giả đã được cập nhật
        authorRepository.save(author);

        // Trả về AuthorResponseDTO đã chuyển đổi từ đối tượng Author
        return convertToDTO(author);
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

        // Ánh xạ giải thưởng
        List<AchievementInfoDTO> achievementInfoDTOs = author.getListAchievements().stream()
                .map(achievement -> {
                    AchievementInfoDTO achievementInfoDTO = new AchievementInfoDTO();
                    achievementInfoDTO.setId(achievement.getId());
                    achievementInfoDTO.setName(achievement.getName());
                    return achievementInfoDTO;
                })
                .collect(Collectors.toList());
        authorResponseDTO.setListAchievements(achievementInfoDTOs);

        // Ánh xạ danh sách ảnh
        List<ImageInfoDTO> imageInfoDTOS = author.getListImages().stream()
                .map(image -> {
                    ImageInfoDTO imageDTO = new ImageInfoDTO();
                    imageDTO.setUrl(image.getUrl());
                    imageDTO.setDescription(image.getDescription());
                    return imageDTO;
                })
                .collect(Collectors.toList());
        authorResponseDTO.setListImages(imageInfoDTOS);

        return authorResponseDTO;
    }

}
