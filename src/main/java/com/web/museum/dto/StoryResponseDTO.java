package com.web.museum.dto;

import com.web.museum.util.StatusType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoryResponseDTO {
    private int id;
    private String title;
    private String content;
    private AuthorInfoDTO author;
    private UserInfoDTO user;

    private StatusType status;
}
