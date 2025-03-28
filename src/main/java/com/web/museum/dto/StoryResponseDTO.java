package com.web.museum.dto;

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
}
