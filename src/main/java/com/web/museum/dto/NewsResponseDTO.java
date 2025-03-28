package com.web.museum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NewsResponseDTO {
    private int id;
    private String title;
    private String writer;
    private String description;
    private String content;
    private int views;
    private int saves;

}
