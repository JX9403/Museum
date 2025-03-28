package com.web.museum.controller;

import com.web.museum.dto.PagedResponse;
import com.web.museum.dto.NewsInfoDTO;
import com.web.museum.dto.NewsResponseDTO;
import com.web.museum.entity.News;
import com.web.museum.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<PagedResponse<NewsInfoDTO>> getAllNews(
            @RequestParam int page,
            @RequestParam int size ){
        Pageable pageable = PageRequest.of(page, size);
        Page<NewsInfoDTO> news = newsService.getAllNews(pageable);

        PagedResponse<NewsInfoDTO> response = new PagedResponse<>(
                news.getContent(),
                news.getNumber(),
                news.getSize(),
                news.getTotalElements(),
                news.getTotalPages()
        );

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getNewsById (@PathVariable int id) {
        NewsResponseDTO newsResponseDTO = newsService.getNewsById(id);
        return ResponseEntity.ok(newsResponseDTO);
    }

    @PostMapping
    public ResponseEntity<NewsResponseDTO> createNews ( @RequestBody News news){
        NewsResponseDTO newsResponseDTO = newsService.createNews(news);
        return ResponseEntity.ok(newsResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> updateNews(@PathVariable int id, @RequestBody News reqNews){
        NewsResponseDTO newsResponseDTO = newsService.updateNews(id,reqNews);
        return ResponseEntity.ok(newsResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable int id ){
        newsService.deleteNews(id);
        return ResponseEntity.ok("Success delete news id = " + "id");
    }
}
