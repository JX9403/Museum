package com.web.museum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WorkInfoDTO {
    private int id;
    private String title;
    private AuthorInfoDTO author;
    private UserInfoDTO user;
    private int views;
    private int saves;

}
