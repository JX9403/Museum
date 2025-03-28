package com.web.museum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NewsInfoDTO {
    private int id;
    private String title;
    private String writer;
    private String description;
    private int views;
    private int saves;

}
