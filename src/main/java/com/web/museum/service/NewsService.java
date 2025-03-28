package com.web.museum.service;

import com.web.museum.dto.NewsInfoDTO;
import com.web.museum.dto.NewsResponseDTO;
import com.web.museum.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    Page<NewsInfoDTO> getAllNews(Pageable pageable);
    NewsResponseDTO getNewsById ( int id ) ;
    NewsResponseDTO updateNews ( int id, News work );
    void deleteNews ( int id);
    NewsResponseDTO createNews ( News news );

}
