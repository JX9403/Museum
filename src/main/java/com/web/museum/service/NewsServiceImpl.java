package com.web.museum.service;

import com.web.museum.dao.AuthorRepository;
import com.web.museum.dao.UserRepository;
import com.web.museum.dao.NewsRepository;
import com.web.museum.dto.AuthorInfoDTO;
import com.web.museum.dto.UserInfoDTO;
import com.web.museum.dto.NewsInfoDTO;
import com.web.museum.dto.NewsResponseDTO;
import com.web.museum.entity.Author;
import com.web.museum.entity.User;
import com.web.museum.entity.News;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService{
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public Page<NewsInfoDTO> getAllNews(Pageable pageable) {
        Page<News> news = newsRepository.findAll(pageable);
        return news.map(this::convertToInfoDTO);
    }

    @Override
    public NewsResponseDTO getNewsById(int id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if(newsOptional.isEmpty()){
            throw new RuntimeException("News not found");
        }
        News news = newsOptional.get();

        news.setViews(news.getViews()+1);
        newsRepository.save(news);

        return convertToDTO(news);
    }

    @Override
    @Transactional
    public NewsResponseDTO updateNews(int id, News reqNews) {

        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            throw new RuntimeException("News not found");
        }
        News news = newsOptional.get();

        if (reqNews.getTitle() == null || reqNews.getTitle().isEmpty()) {
            throw new RuntimeException("Title cannot be empty");
        }
        if (reqNews.getContent() == null || reqNews.getContent().isEmpty()) {
            throw new RuntimeException("Content cannot be empty");
        }

        if (reqNews.getDescription() == null || reqNews.getDescription().isEmpty()) {
            throw new RuntimeException("Description cannot be empty");
        }

        if (reqNews.getWriter() == null || reqNews.getWriter().isEmpty()) {
            throw new RuntimeException("Writer cannot be empty");
        }
        news.setTitle(reqNews.getTitle());
        news.setContent(reqNews.getContent());
        news.setDescription(reqNews.getDescription());
        news.setWriter(reqNews.getWriter());

        News updatedNews = newsRepository.save(news);
        return convertToDTO(updatedNews);
    }


    @Override
    @Transactional
    public void deleteNews(int id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if(newsOptional.isEmpty()){
            throw new RuntimeException("News not found");
        }
        News news = newsOptional.get();
        newsRepository.delete(news);
    }

    @Override
    @Transactional
    public NewsResponseDTO createNews(News reqNews) {


        if (reqNews.getTitle() == null || reqNews.getTitle().isEmpty()) {
            throw new RuntimeException("Title cannot be empty");
        }
        if (reqNews.getContent() == null || reqNews.getContent().isEmpty()) {
            throw new RuntimeException("Content cannot be empty");
        }
        if (reqNews.getDescription() == null || reqNews.getDescription().isEmpty()) {
            throw new RuntimeException("Description cannot be empty");
        }
        if (reqNews.getWriter() == null || reqNews.getWriter().isEmpty()) {
            throw new RuntimeException("Writer cannot be empty");
        }

        News newNews = newsRepository.save(reqNews);
        return convertToDTO(newNews);
    }



    private NewsResponseDTO convertToDTO(News news) {
        NewsResponseDTO NewsResponseDTO = new NewsResponseDTO();
        NewsResponseDTO.setId(news.getId());
        NewsResponseDTO.setTitle(news.getTitle());
        NewsResponseDTO.setWriter(news.getWriter());
        NewsResponseDTO.setDescription(news.getDescription());
        NewsResponseDTO.setContent(news.getContent());
        NewsResponseDTO.setViews(news.getViews());
        NewsResponseDTO.setSaves(news.getSaves());

        return NewsResponseDTO;
    }

    private NewsInfoDTO convertToInfoDTO(News news) {
        NewsInfoDTO NewsInfoDTO = new NewsInfoDTO();
        NewsInfoDTO.setId(news.getId());
        NewsInfoDTO.setTitle(news.getTitle());
        NewsInfoDTO.setWriter(news.getWriter());
        NewsInfoDTO.setDescription(news.getDescription());
        NewsInfoDTO.setViews(news.getViews());
        NewsInfoDTO.setSaves(news.getSaves());

        return NewsInfoDTO;
    }

}
