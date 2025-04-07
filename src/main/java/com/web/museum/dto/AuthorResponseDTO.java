package com.web.museum.dto;

import com.web.museum.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponseDTO {
    private int id;
    private String name;
    private int birthYear;
    private int deathYear;
    private String biography;
    private String career;
    private String type;
    private List<AchievementInfoDTO> listAchievements;
    private List<ImageInfoDTO> listImages;
}
