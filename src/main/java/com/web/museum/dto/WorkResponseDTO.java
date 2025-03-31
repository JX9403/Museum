package com.web.museum.dto;

import com.web.museum.util.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkResponseDTO {
    private int id;
    private String title;
    private String content;
    private StatusType status;
    private AuthorInfoDTO author;
    private UserInfoDTO user;
}
